package com.tanylog.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.setMaxElementsForPrinting;

import com.tanylog.post.controller.response.PostRead;
import com.tanylog.post.controller.response.PostReads;
import com.tanylog.post.domain.Post;
import com.tanylog.post.repository.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

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
  void 게시글_1페이지_조회() {
    // given
    List<Post> posts = IntStream.range(1, 31)
        .mapToObj(i -> Post.builder()
            .title("title - " + i)
            .content("content - " + i)
            .build())
        .collect(Collectors.toList());

    postRepository.saveAll(posts);

    PageRequest pageable = PageRequest.of(1, 5);

    // when
    PostReads postReads = postService.readAll(pageable);

    // then
    assertThat(postReads.getPostReads().size()).isEqualTo(5);
    assertThat(postReads.getPostReads().get(0).getTitle()).isEqualTo("title - 1");
    assertThat(postReads.getPostReads().get(4).getTitle()).isEqualTo("title - 5");
  }
}