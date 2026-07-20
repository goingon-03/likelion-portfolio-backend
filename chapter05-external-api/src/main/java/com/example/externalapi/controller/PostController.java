package com.example.externalapi.controller;

import com.example.externalapi.dto.PostResponse;
import com.example.externalapi.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{postId}")
    public PostResponse getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @GetMapping("/delay")
    public PostResponse getDelayedResponse() {
        return postService.getDelayedResponse();
    }

    @GetMapping("/retry")
    public PostResponse getPostWithRetry() {
        return postService.getPostWithRetry();
    }

    @GetMapping("/bad-request")
    public void callBadRequestApi() {
        postService.callBadRequestApi();
    }
}