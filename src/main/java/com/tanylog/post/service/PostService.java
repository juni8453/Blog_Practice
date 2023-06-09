package com.tanylog.post.service;

import com.tanylog.post.controller.request.PostCreate;
import com.tanylog.post.controller.request.PostSearch;
import com.tanylog.post.controller.response.PostRead;
import com.tanylog.post.controller.response.PostReads;
import com.tanylog.post.domain.Post;
import com.tanylog.post.repository.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

  private final PostRepository postRepository;

  public PostRead write(PostCreate createPost) {
    Post post = Post.builder()
        .title(createPost.getTitle())
        .content(createPost.getContent())
        .build();

    Post savePost = postRepository.save(post);

    return PostRead.builder()
        .id(savePost.getId())
        .title(savePost.getTitle())
        .content(savePost.getContent())
        .build();
  }

  public PostRead read(Long postId) {
    Post findPost = postRepository.findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 게시글입니다."));

    return PostRead.builder()
        .id(findPost.getId())
        .title(findPost.getTitle())
        .content(findPost.getContent())
        .build();
  }

  public PostReads readAll(PostSearch postSearch) {
    List<PostRead> postReads = postRepository.readAll(postSearch).stream()
        .map(pagePost -> PostRead.builder()
            .id(pagePost.getId())
            .title(pagePost.getTitle())
            .content(pagePost.getContent())
            .build())
        .collect(Collectors.toList());

    return PostReads.builder()
        .postReads(postReads)
        .build();
  }
}
