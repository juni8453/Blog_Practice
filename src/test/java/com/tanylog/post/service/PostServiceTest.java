package com.tanylog.post.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.tanylog.post.controller.response.PostRead;
import com.tanylog.post.controller.response.PostReads;
import com.tanylog.post.domain.Post;
import com.tanylog.post.repository.PostRepository;
import java.util.List;
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
    Post post = Post.builder()
        .title("title")
        .content("content")
        .build();

    // when
    postRepository.save(post);

    // then
    assertThat(postRepository.count()).isEqualTo(1L);

    Post findPost = postRepository.findAll().get(0);
    assertThat(findPost.getTitle()).isEqualTo("title");
    assertThat(findPost.getContent()).isEqualTo("content");
  }

  @Test
  void 게시글_단건조회() {
    // given
    Long postId = 1L;

    Post post = Post.builder()
        .title("title")
        .content("content")
        .build();

    // when
    postRepository.save(post);
    PostRead findPost = postService.read(postId);

    // then
    assertThat(findPost.getTitle()).isEqualTo("title");
    assertThat(findPost.getContent()).isEqualTo("content");
  }

  @Test
  void 게시글_전체조회() {
    postRepository.saveAll(List.of(
        Post.builder()
            .title("titleA")
            .content("contentA")
            .build(),

        Post.builder()
            .title("titleB")
            .content("contentB")
            .build()
    ));

    // when
    PostReads postReads = postService.readAll();

    // then
    assertThat(postReads.getPostReads().size()).isEqualTo(2);
    assertThat(postReads.getPostReads().get(0).getTitle()).isEqualTo("titleA");
    assertThat(postReads.getPostReads().get(1).getTitle()).isEqualTo("titleB");
  }
}