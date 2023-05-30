package com.tanylog.post.service;

import com.tanylog.post.controller.request.PostCreate;
import com.tanylog.post.controller.response.PostRead;
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

  public void write(PostCreate createPost) {
    Post post = Post.builder()
        .title(createPost.getTitle())
        .content(createPost.getContent())
        .build();

    postRepository.save(post);
  }

  public PostRead read(Long postId) {
    Post findPost = postRepository.findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 게시글입니다."));

    return PostRead.builder()
        .title(findPost.getTitle())
        .content(findPost.getContent())
        .build();
  }
}
