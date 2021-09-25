package com.orange.mainservice.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orange.mainservice.config.UnsecuredWebMvcTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static com.orange.mainservice.utils.DummyConstants.DUMMY_LONG;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@UnsecuredWebMvcTest(CommentController.class)
class CommentControllerIT {

    private static final String URI = "/comments";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @Test
    void getById_ShouldReturnResponseDTO() throws Exception {
        // given
        CommentResponse commentResponse = getExampleCommentResponse();
        when(commentService.getResponseById(commentResponse.getId())).thenReturn(commentResponse);

        // when
        // then
        mvc.perform(get(String.format("%s/%s", URI, commentResponse.getId())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(commentResponse.getId()))
                .andExpect(jsonPath("$.body").value(commentResponse.getBody()))
                .andExpect(jsonPath("$.username").value(commentResponse.getUsername()))
                .andExpect(jsonPath("$.userImg").value(commentResponse.getUserImg()));

        verify(commentService).getResponseById(commentResponse.getId());
    }

    @Test
    void createShouldReturnResponseDto() throws Exception {
        // given
        String commentRequest = getExampleCommentRequestAsJson();
        CommentResponse commentResponse = getExampleCommentResponse();
        when(commentService.createComment(any(CommentRequest.class))).thenReturn(commentResponse);

        // when
        // then
        mvc.perform(post(URI)
                .content(commentRequest)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(commentResponse.getId()))
                .andExpect(jsonPath("$.body").value(commentResponse.getBody()))
                .andExpect(jsonPath("$.username").value(commentResponse.getUsername()))
                .andExpect(jsonPath("$.userImg").value(commentResponse.getUserImg()))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", containsString(commentResponse.getId().toString())));
        verify(commentService).createComment(any(CommentRequest.class));
    }

    @Test
    void editShouldReturnResponseDto() throws Exception {
        // given
        String commentRequest = getExampleCommentRequestAsJson();
        CommentResponse commentResponse = getExampleCommentResponse();
        when(commentService.editComment(anyLong(), any(CommentRequest.class))).thenReturn(commentResponse);

        // when
        // then
        mvc.perform(put(String.format("%s/%s", URI, commentResponse.getId()))
                .content(commentRequest)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(commentResponse.getId()))
                .andExpect(jsonPath("$.body").value(commentResponse.getBody()))
                .andExpect(jsonPath("$.username").value(commentResponse.getUsername()))
                .andExpect(jsonPath("$.userImg").value(commentResponse.getUserImg()));
        verify(commentService).editComment(anyLong(), any(CommentRequest.class));
    }

    @Test
    void deleteShouldReturnOk() throws Exception {

        mvc.perform(delete(String.format("%s/%s", URI, DUMMY_LONG)))
                .andDo(print())
                .andExpect(status().isOk());
        verify(commentService).deleteComponent(DUMMY_LONG);
    }

    private CommentResponse getExampleCommentResponse() {
        return new CommentResponse(
                DUMMY_LONG,
                "example comment",
                new Date(),
                "example username",
                "example img"
        );
    }

    private String getExampleCommentRequestAsJson() throws JsonProcessingException {
        var exampleCommentDto = new CommentRequest(
                DUMMY_LONG,
                "example body",
                DUMMY_LONG
        );
        return new ObjectMapper().writeValueAsString(exampleCommentDto);
    }
}
