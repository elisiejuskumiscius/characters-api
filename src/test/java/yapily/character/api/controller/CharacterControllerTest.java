package yapily.character.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getCharacters__returnsIDs() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/characters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andReturn();
    }

    @Test
    void getCharacter_existingID_returnsError() throws Exception {
        int characterId = 1010761;
        mvc.perform(MockMvcRequestBuilders.get("/characters/" + characterId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1010761))
                .andExpect(jsonPath("$.name").value("Sleepwalker"))
                .andExpect(jsonPath("$.description").value("Sleepwalkers are guardians of the Mindscape -- they apprehended beings that invade the sleeping minds of humans."))
                .andExpect(jsonPath("$.thumbnail.path").value("http://i.annihil.us/u/prod/marvel/i/mg/f/30/4c0037640501a"))
                .andExpect(jsonPath("$.thumbnail.extension").value("jpg"))
                .andReturn();
    }
}