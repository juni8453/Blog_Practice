package com.tanylog.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.tanylog.exception.customException.PostNotFound;
import com.tanylog.post.controller.request.PostEdit;
import com.tanylog.post.controller.request.PostSearch;
import com.tanylog.post.controller.response.PostRead;
import com.tanylog.post.controller.response.PostReads;
import com.tanylog.post.domain.Post;
import com.tanylog.post.repository.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    Post post = Post.builder()
        .title("title")
        .content("content")
        .build();

    postRepository.save(post);

    // when
    PostRead findPost = postService.read(post.getId());

    // then
    assertThat(findPost.getTitle()).isEqualTo("title");
    assertThat(findPost.getContent()).isEqualTo("content");
  }

  @Test
  @DisplayName("게시글 단건 조회 - 존재하지 않는 글")
  void 게시글_단건조회_실패() {
    Post post = Post.builder()
        .title("title")
        .content("content")
        .build();

    Post save = postRepository.save(post);

    // when & then
    assertThrows(PostNotFound.class, () -> {
      postService.read(save.getId() + 1L);
    });
  }

  @Test
  void 게시글_1페이지_조회() {
    // given
    List<Post> posts = IntStream.range(0, 20)
        .mapToObj(i -> Post.builder()
            .title("title - " + i)
            .content("content - " + i)
            .build())
        .collect(Collectors.toList());

    postRepository.saveAll(posts);

    PostSearch postSearch = PostSearch.builder()
        .page(1)
        .size(10)
        .build();

    // when
    PostReads postReads = postService.readAll(postSearch);

    // then
    assertThat(postReads.getPostReads().size()).isEqualTo(10);
    assertThat(postReads.getPostReads().get(0).getTitle()).isEqualTo("title - 19");
  }

  @Test
  void 게시글_제목_수정() {
    // given
    Post post = Post.builder()
        .title("수정 전 title")
        .content("수정 전 content")
        .build();

    Post save = postRepository.save(post);

    PostEdit edit = PostEdit.builder()
        .title("수정 후 title")
        .content(null)
        .build();

    // when
    postService.edit(save.getId(), edit);

    // then
    Post edited = postRepository.findById(save.getId())
        .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + save.getId()));

    assertThat(edited.getTitle()).isEqualTo("수정 후 title");
    assertThat(edited.getContent()).isEqualTo("수정 전 content");
  }

  @Test
  void 게시글_내용_수정() {
    // given
    Post post = Post.builder()
        .title("수정 전 title")
        .content("수정 전 content")
        .build();

    Post save = postRepository.save(post);

    PostEdit edit = PostEdit.builder()
        .title("수정 전 title")
        .content("수정 후 content")
        .build();

    // when
    postService.edit(save.getId(), edit);

    // then
    Post edited = postRepository.findById(save.getId())
        .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + save.getId()));

    assertThat(edited.getTitle()).isEqualTo("수정 전 title");
    assertThat(edited.getContent()).isEqualTo("수정 후 content");
  }

  @Test
  @DisplayName("게시글 내용 수정 - 존재하지 않는 글")
  void 게시글_수정_실패() {
    // given
    Post post = Post.builder()
        .title("수정 전 title")
        .content("수정 전 content")
        .build();

    Post save = postRepository.save(post);

    PostEdit edit = PostEdit.builder()
        .title("수정 전 title")
        .content("수정 후 content")
        .build();

    // when & then
    assertThrows(PostNotFound.class, () -> {
      postService.edit(save.getId() + 1L, edit);
    });
  }

  @Test
  void 게시글_삭제() {
    // given
    List<Post> posts = postRepository.saveAll(IntStream.range(0, 2)
        .mapToObj(i -> Post.builder()
            .title("title " + i)
            .content("content " + i)
            .build())
        .collect(Collectors.toList()));

    // when
    postService.delete(posts.get(0).getId());

    // then
    assertThat(postRepository.count()).isEqualTo(1);
    assertThat(postRepository.findAll().get(0).getTitle()).isEqualTo("title 1");
  }

  @Test
  @DisplayName("게시글 삭제 - 존재하지 않는 ")
  void 게시글_삭제_실패() {
    // given
    Post post = Post.builder()
        .title("title")
        .content("content")
        .build();

    Post save = postRepository.save(post);

    // when & then
    assertThrows(PostNotFound.class, () -> {
      postService.delete(save.getId() + 1L);
    });
  }
}
