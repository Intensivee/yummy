package com.orange.mainservice.component;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.orange.mainservice.utils.DummyConstants.DUMMY_LONG;
import static com.orange.mainservice.utils.DummyConstants.DUMMY_STRING;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ComponentControllerIT {

    private static final String URI = "/components";
    private static Long testComponentId;

    @Autowired
    ComponentRepository componentRepository;

    @Autowired
    private MockMvc mvc;

    @AfterAll
    static void afterAll(@Autowired ComponentRepository componentRepository) {
        if (componentRepository.existsById(testComponentId)) {
            componentRepository.deleteById(testComponentId);
        }
    }

    @Test
    @DisplayName("get by id - when exists - should return response with dto and 200 http code")
    void getById_whenExists_ShouldReturnResponseWithDtoAnd200HttpCode() throws Exception {
        var component = new Component(DUMMY_STRING, true, true);
        testComponentId = componentRepository.saveAndFlush(component).getId();

        mvc.perform(get(String.format("%s/%s", URI, testComponentId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testComponentId))
                .andExpect(jsonPath("$.name").value(DUMMY_STRING));

        componentRepository.deleteById(testComponentId);
    }

    @Test
    @DisplayName("get by id - when not exists - should return empty response with 404 http code")
    void getById_whenNotExists_ShouldReturnEmptyResponseWith404HttpCode() throws Exception {
        String expectedErrorMessage = "Resource: Component with id = '" + DUMMY_LONG + "' not found.";
        mvc.perform(get(String.format("%s/%s", URI, DUMMY_LONG)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(content().contentType(APPLICATION_JSON));
    }
}
