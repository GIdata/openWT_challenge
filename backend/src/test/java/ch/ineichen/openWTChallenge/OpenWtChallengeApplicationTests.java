package ch.ineichen.openWTChallenge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc()
class OpenWtChallengeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoatController controller;


    @Test
    public void contextLoads() {
        assertNotNull(controller);
    }

    @Test
    public void getAllBoatsTest() throws Exception {
        controller.boats.clear();
        mockMvc.perform(get("/boats/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        var boat = new Boat("Test", "Description");
        controller.boats.add(boat);
        mockMvc.perform(get("/boats/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(boat.name)))
                .andExpect(jsonPath("$[0].description", is(boat.description)))
                .andExpect(jsonPath("$[0].id", is(boat.id)));
    }

    @Test
    public void getSingleBoatTest() throws Exception {
        var boat = new Boat("Test", "Test description");
        controller.boats.add(boat);
        mockMvc.perform(get("/boats/" + boat.id))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\": \"Test\", \"description\": \"Test description\"}"));
    }

    @Test
    public void getNotExistingBoats() throws Exception {
        mockMvc.perform(get("/boats/999"))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/boats/-1"))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/boats/someId"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addBoat() throws Exception {
        controller.boats.clear();
        var result = mockMvc.perform(post("/boats/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""" 
                                {"name": "The unsinkable Titanic", "description": "Test Description"}
                                """))
                .andExpect(status().isOk())
                .andReturn();
        int id = Integer.parseInt(result.getResponse().getContentAsString());
        assertEquals(1, controller.boats.size());
        var boat = controller.boats.get(0);
        assertEquals("The unsinkable Titanic", boat.name);
        assertEquals("Test Description", boat.description);
        assertEquals(boat.id, id);

        mockMvc.perform(post("/boats/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name": "Test2", "description": "Test Description 2"}
                        """));

        assertEquals(2, controller.boats.size());
    }

    @Test
    public void deleteBoat() throws Exception {
        controller.boats.clear();
        var boat = new Boat("Deletable Boat", "Created to be deleted");
        controller.boats.add(boat);
        assertEquals(1, controller.boats.size());
        mockMvc.perform(delete("/boats/" + boat.id))
                .andExpect(status().isOk());
        assertEquals(0, controller.boats.size());

        mockMvc.perform(delete("/boats/" + boat.id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteNotExistingBoats() throws Exception {
        controller.boats.clear();
        mockMvc.perform(delete("/boats/-1"))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/boats/00000009"))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/boats/someId"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateBoat() throws Exception {
        controller.boats.clear();
        var boat = new Boat("Original Boat", "Original Description");
        final int id = boat.id;
        controller.boats.add(boat);

        mockMvc.perform(put("/boats/" + boat.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name": "A new name", "description": "new description"}
                        """));
        assertEquals("A new name", boat.name);
        assertEquals("new description", boat.description);
        assertEquals(id, boat.id);
    }
}