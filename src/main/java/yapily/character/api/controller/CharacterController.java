package yapily.character.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import yapily.character.api.model.Character;
import yapily.character.api.service.CharacterService;

import java.util.Set;

@RestController
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @GetMapping("/characters")
    public Set<Long> getCharacters() {
    	return characterService.getAllCharacters();
    }

    @GetMapping("/characters/{characterId}")
    public Character getCharacterById(
    		@PathVariable long characterId) {
		return characterService.getCharacterById(characterId);
    }

}
