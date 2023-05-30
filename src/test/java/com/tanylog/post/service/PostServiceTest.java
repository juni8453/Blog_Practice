package com.tanylog.post.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.tanylog.post.controller.request.PostCreate;
import com.tanylog.post.controller.response.PostRead;
import com.tanylog.post.domain.Post;
import com.tanylog.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceTest {

  @Autowired
  private PostService postService;

  @Autowired
  private PostRepository postRepository;

  @BeforeEach
  void clean() {
    postRepository.deleteAll();
  }

  @Test
  void 게시글_생성() {
    // given
    PostCreate postCreate = PostCreate.builder()
        .title("title")
        .content("content")
        .build();

    // when
    postService.write(postCreate);

    // then
    assertThat(postRepository.count()).isEqualTo(1L);

    Post findPost = postRepository.findAll().get(0);
    assertThat(findPost.getTitle()).isEqualTo("title");
    assertThat(findPost.getContent()).isEqualTo("content");
  }

  @Test
  void 게시글_단건조회() {
    // given
    Long PostId = 1L;

    PostCreate postCreate = PostCreate.builder()
        .title("title")
        .content("content")
        .build();

    // when
    postService.write(postCreate);
    PostRead findPost = postService.read(PostId);

    // then
    assertThat(findPost.getTitle()).isEqualTo("title");
    assertThat(findPost.getContent()).isEqualTo("content");


  }
}