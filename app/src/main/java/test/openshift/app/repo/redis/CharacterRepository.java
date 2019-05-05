package test.openshift.app.repo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import test.openshift.app.bean.ComicCharacter;

import java.util.*;

@Repository
public class CharacterRepository {

    private static final String NAMESPACE = "character";
    private static final String SEPARATOR = ":";
    private static final String NAMESET = NAMESPACE + "-name-set";

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void add(ComicCharacter character) {
        BoundHashOperations<String, String, String> op = redisTemplate.boundHashOps(key(character.getId()));
        op.putAll(this.toMap(character));

    }

    public ComicCharacter get(Long id) {
        return get(key(id));
    }

    public boolean delete(Long id){
        String key = key(id);
        return this.redisTemplate.delete(key);
    }

    private ComicCharacter get(String key) {
        BoundHashOperations<String, String, String> op = this.redisTemplate.boundHashOps(key);
        Map<String, String> m = op.entries();
        ComicCharacter u = this.fromMap(m);
        return u;
    }

    public List<ComicCharacter> list(){
        Set<String> keys = this.redisTemplate.keys(key("*"));
        List<ComicCharacter> users = new ArrayList<>(keys.size());
        for (String key : keys) {
            users.add(this.get(key));
        }
        return users;
    }

    // ============== use name to deduplicate =================
    public void addNameToSet(String name){
        this.redisTemplate.boundSetOps(NAMESET).add(name.toLowerCase());
    }

    /**
     *
     * @param name
     * @return true if name exists; error in transaction, see doc BoundSetOperations#isMember(java.lang.Object)
     */
    public boolean checkNameExists(String name){
        BoundSetOperations op = this.redisTemplate.boundSetOps(NAMESET);
        return op.isMember(name.toLowerCase());
    }

    public void removeNameFromSet(String name) {
        this.redisTemplate.boundSetOps(NAMESET).remove(name.toLowerCase());
    }













    private String key(Object id) {
        return NAMESPACE + SEPARATOR + id;
    }

    private ComicCharacter fromMap(Map<String, String> map) {
        if(map == null || map.isEmpty())
            return null;
        ComicCharacter u = new ComicCharacter();
        u.setId(Long.parseLong(map.get("id")));
        u.setNickname(map.get("nickname"));
        u.setName(map.get("name"));
        u.setCity(map.get("city"));
        return u;
    }

    private Map<String, String> toMap(ComicCharacter user) {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(user.getId()));
        map.put("name", user.getName());
        map.put("nickname", user.getNickname());
        map.put("city", user.getCity());
        return map;
    }
}
