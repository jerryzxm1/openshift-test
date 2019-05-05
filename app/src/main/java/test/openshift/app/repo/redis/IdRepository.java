package test.openshift.app.repo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IdRepository {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public Long nextId(){
        return redisTemplate.boundValueOps("id").increment();
    }
}
