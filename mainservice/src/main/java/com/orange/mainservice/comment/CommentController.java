package com.orange.mainservice.comment;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("comments")
@AllArgsConstructor
class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.getResponseById(id));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(@Valid @RequestBody CommentRequest request) {
        CommentResponse created = commentService.createComment(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> edit(@PathVariable("id") Long id,
                                                @Valid @RequestBody CommentRequest request){
        CommentResponse edited = commentService.editComment(id, request);
        return ResponseEntity.ok(edited);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        commentService.deleteComponent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("search/findByRecipeId/{id}")
    public ResponseEntity<List<CommentResponse>> getByRecipeId(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getByRecipeId(id));
    }
}
