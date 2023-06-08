package com.tanylog.post.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanylog.post.controller.request.PostCreate;
import com.tanylog.post.domain.Post;
import com.tanylog.post.repository.PostRepository;
import com.tanylog.post.service.PostService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class PostApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PostService postService;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private ObjectMapper mapper;

  @BeforeEach
  void clean() {
    postRepository.deleteAll();
  }

  @Test
  void 게시글_생성() throws Exception {
    // given
    String title = "제목입니다.";
    String content = "내용입니다.";
    PostCreate postCreate = PostCreate.builder()
        .title(title)
        .content(content)
        .build();

    mockMvc.perform(post("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(postCreate)))
        .andExpect(status().isOk())

        .andDo(print());
  }

  @Test
  @DisplayName("/api/posts 요청 시 title 값은 필수다.")
  void 게시글_생성_실패() throws Exception {
    // given
    String content = "내용입니다.";
    PostCreate postCreate = PostCreate.builder()
        .content(content)
        .build();

    mockMvc.perform(post("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(postCreate)))

        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
        .andExpect(jsonPath("$.validation.title").value("title 을 입력해주세요."))

        .andDo(print());
  }

  @Test
  @DisplayName("/api/posts 요청 시 DB 에 값이 저장된다.")
  void test3() throws Exception {
    // given
    String title = "제목입니다.";
    String content = "내용입니다.";
    PostCreate postCreate = PostCreate.builder()
        .title(title)
        .content(content)
        .build();

    mockMvc.perform(post("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(postCreate)))
        .andExpect(status().isOk())

        .andDo(print());

    // then
    assertThat(postRepository.count()).isEqualTo(1L);
  }

  @Test
  @DisplayName("게시글 ID 로 해당 게시글을 조회한다.")
  void read() throws Exception {
    // given
    PostCreate postCreate = PostCreate.builder()
        .title("title")
        .content("content")
        .build();

    postService.write(postCreate);

    // when & then
    mockMvc.perform(get("/api/posts/" + 1L)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("title"))
        .andExpect(jsonPath("$.content").value("content"))

        .andDo(print());
  }

  @Test
  @DisplayName("첫 번째 게시글 페이지를 조회한다.")
  void readAll() throws Exception {

    // given
    List<Post> posts = IntStream.range(1, 31)
        .mapToObj(i -> Post.builder()
            .title("title - " + i)
            .content("content - " + i)
            .build())
        .collect(Collectors.toList());

    postRepository.saveAll(posts);

    // when & then
    mockMvc.perform(get("/api/posts?page=1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.postReads.length()", is(5)))
        .andExpect(jsonPath("$.postReads[0].id").value(1L))

        .andDo(print());
  }
}