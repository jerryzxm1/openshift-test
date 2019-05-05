package test.openshift.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.openshift.app.bean.ComicCharacter;
import test.openshift.app.repo.redis.CharacterRepository;
import test.openshift.app.repo.redis.IdRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CharacterService {

    @Autowired
    private IdRepository idRepository;

    @Autowired
    private CharacterRepository characterRepository;

    public ComicCharacter add(ComicCharacter character) {
        if(Objects.nonNull(character.getId())) {
            log.warn("Already has a id: {}", character);
            return character;
        }

        if(characterRepository.checkNameExists(character.getName())) {
            log.warn("Duplicate name: {}", character);
            return character;
        }
        character.setId(idRepository.nextId());
        this.characterRepository.add(character);
        this.characterRepository.addNameToSet(character.getName());
        log.info("ComicCharacter added: {}", character);
        return character;
    }

    public ComicCharacter get(Long id) {
        return this.characterRepository.get(id);
    }

    public boolean delete(Long id) {
        ComicCharacter character = characterRepository.get(id);
        boolean succ = this.characterRepository.delete(id);
        if(succ)
            this.characterRepository.removeNameFromSet(character.getName());
        return succ;
    }

    public List<ComicCharacter> list() {
        List<ComicCharacter> users = this.characterRepository.list();
        Collections.sort(users);
        return users;
    }
}
