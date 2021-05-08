package com.orange.mainservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orange.mainservice.request.CommentRequest;
import com.orange.mainservice.response.CommentResponse;
import com.orange.mainservice.service.CommentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
class CommentControllerIntegrationTest {

    private static final String URI = "/comments";
    private static CommentRequest requestBody;
    private static String requestBodyJson;
    private static CommentResponse response;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @BeforeAll
    static void beforeAll() throws JsonProcessingException {
        response = new CommentResponse(
                1L,
                "example comment",
                new Date(),
                "example username",
                "example img"
        );

         requestBody = new CommentRequest(
                1L,
                "example body",
                1L,
                1L
        );

        ObjectMapper objectMapper = new ObjectMapper();
        requestBodyJson = objectMapper.writeValueAsString(requestBody);
    }

    @Test
    void getByIdShouldReturnResponseDTO() throws Exception {

        when(commentService
                .getResponseById(response.getId()))
                .thenReturn(response);

        mvc.perform(get(String.format("%s/%s", URI, response.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.body").value(response.getBody()))
                .andExpect(jsonPath("$.username").value(response.getUsername()))
                .andExpect(jsonPath("$.userImg").value(response.getUserImg()));
        verify(commentService).getResponseById(response.getId());
    }

    @Test
    void createShouldReturnResponseDto() throws Exception {

        when(commentService
                .add(any(CommentRequest.class)))
                .thenReturn(response);

        mvc.perform(post(URI)
                .content(requestBodyJson)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.body").value(response.getBody()))
                .andExpect(jsonPath("$.username").value(response.getUsername()))
                .andExpect(jsonPath("$.userImg").value(response.getUserImg()))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", containsString(response.getId().toString())));
        verify(commentService).add(any(CommentRequest.class));
    }

    @Test
    void editShouldReturnResponseDto() throws Exception {

        when(commentService
                .edit(anyLong(), any(CommentRequest.class)))
                .thenReturn(response);

        mvc.perform(put(String.format("%s/%s", URI, requestBody.getId()))
                .content(requestBodyJson)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.body").value(response.getBody()))
                .andExpect(jsonPath("$.username").value(response.getUsername()))
                .andExpect(jsonPath("$.userImg").value(response.getUserImg()));
        verify(commentService).edit(anyLong(), any(CommentRequest.class));
    }

    @Test
    void deleteShouldReturnOk() throws Exception{

        mvc.perform(delete(String.format("%s/%s", URI, 1L)))
                .andDo(print())
                .andExpect(status().isOk());
        verify(commentService).delete(1L);
    }
}
