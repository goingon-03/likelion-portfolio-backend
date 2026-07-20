package com.example.externalapi.service;

import com.example.externalapi.client.PostClient;
import com.example.externalapi.dto.PostResponse;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostClient postClient;

    public PostService(PostClient postClient) {
        this.postClient = postClient;
    }

    public PostResponse getPost(Long postId) {
        return postClient.getPost(postId);
    }

    public PostResponse getDelayedResponse() {
        return postClient.getDelayedResponse();
    }

    public PostResponse getPostWithRetry() {
        return postClient.getPostWithRetry();
    }

    public void callBadRequestApi() {
        postClient.callBadRequestApi();
    }
}