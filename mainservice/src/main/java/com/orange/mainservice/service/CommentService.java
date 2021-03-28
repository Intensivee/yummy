package com.orange.mainservice.service;

import com.orange.mainservice.entity.Comment;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.mapper.response.CommentResponseMapper;
import com.orange.mainservice.repository.CommentRepository;
import com.orange.mainservice.response.CommentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentResponseMapper commentMapper;

    public CommentResponse getResponseById(Long id){
        return commentMapper.commentToResponse(getById(id));
    }

    private Comment getById(Long id){
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
    }
}
