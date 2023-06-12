package com.tanylog.post.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.tanylog.post.controller.request.PostCreate;
import com.tanylog.post.controller.response.PostRead;
import com.tanylog.post.domain.Post;
import com.tanylog.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {

  // 공식문서에서는 @AutoConfigureMockMvc 로 MockMvc 를 초기화시키지 않고 setUp() 메서드에서 초기화시킨다.
  private MockMvc mockMvc;

  @Autowired
  private PostRepository postRepository;

  @BeforeEach
  void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation))
        .build();
  }

  @BeforeEach
  void clean() {
    postRepository.deleteAll();
  }

  @Test
  @DisplayName("게시글 단건 조회 테스트")
  void docs() throws Exception {
    // given
    Post post = Post.builder()
        .title("title")
        .content("content")
        .build();

    postRepository.save(post);

    this.mockMvc.perform(get("/api/posts/{postId}", post.getId())
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("index"));
  }
}
