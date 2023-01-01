package fr.lernejo.search.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ElasticSearchControllerTest {


    @Test
    void checkHomePage(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc
            .perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void Get2GamesDeveloppedbyEpicGame(@Autowired MockMvc mockMvc) throws Exception {
        String query = "developer:\"Epic Games\"";
        int ExpResult = 2;
        mockMvc
            .perform(MockMvcRequestBuilders.get("/api/games?query=" + query))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void GetNotthing(@Autowired MockMvc mockMvc) throws Exception {
        String query = "";
        mockMvc
            .perform(MockMvcRequestBuilders.get("/api/games?query=" + query))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void api_games_with_no_query_respond_with_status_400(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc
            .perform(MockMvcRequestBuilders.get("/api/games"))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
