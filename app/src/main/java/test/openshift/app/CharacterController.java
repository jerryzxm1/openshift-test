package test.openshift.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.openshift.app.bean.ComicCharacter;
import test.openshift.app.service.CharacterService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/character", produces = MediaType.APPLICATION_JSON_VALUE)
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody @Valid ComicCharacter character) {
        this.characterService.add(character);
        if(character.getId() != null)
            return ResponseEntity.ok(character);
        else
            return ResponseEntity.badRequest().body(character);
    }

    @PostMapping(path = "/batch", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addBatch(@RequestBody @Valid @Size(min = 1) List<ComicCharacter> characters) {
        for (ComicCharacter user : characters) {
            this.characterService.add(user);
        }
        return ResponseEntity.ok(this.characterService.list());
    }



    @GetMapping
    public ResponseEntity<List<ComicCharacter>> list() {
        return ResponseEntity.ok(this.characterService.list());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity get(@PathVariable("id") @Valid @Min(0) Long id) {
        ComicCharacter u = this.characterService.get(id);
        if(u == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(u);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") @Valid @Min(0) Long id) {
        boolean succ = this.characterService.delete(id);
        if(succ)
            return this.list();
        else
            return ResponseEntity.notFound().build();
    }
}
