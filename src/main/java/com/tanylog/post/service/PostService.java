package com.tanylog.post.service;

import com.tanylog.post.controller.request.PostCreate;
import com.tanylog.post.domain.Post;
import com.tanylog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

  private final PostRepository postRepository;

  public void write(PostCreate postCreate) {
    Post post = Post.builder()
        .title(postCreate.getTitle())
        .content(postCreate.getContent())
        .build();

    postRepository.save(post);
  }
}
