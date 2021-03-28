package com.orange.mainservice.service;

import com.orange.mainservice.response.CommentResponse;
import com.orange.mainservice.entity.Comment;
import com.orange.mainservice.mapper.response.CommentResponseMapper;
import com.orange.mainservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentResponseMapper commentMapper;

    @Autowired
    public CommentService(CommentRepository commentRepository, CommentResponseMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public CommentResponse findResponseById(Long id){
        return commentMapper.commentToResponse(findById(id));
    }

    public Comment findById(Long id){
        return commentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
