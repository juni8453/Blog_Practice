package com.tanylog.post.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanylog.post.controller.request.PostCreate;
import com.tanylog.post.domain.Post;
import com.tanylog.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.tanylog.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class PostApiControllerDocTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @Autowired
  private PostRepository postRepository;

  @BeforeEach
  void clean() {
    postRepository.deleteAll();
  }

  @Test
  @DisplayName("게시글 단건 조회")
  void 게시글_단건_조회() throws Exception {
    // given
    Post post = Post.builder()
        .title("title")
        .content("content")
        .build();

    postRepository.save(post);

    // RestDocumentationRequestBuilders 의 HTTP Method 를 사용
    mockMvc.perform(RestDocumentationRequestBuilders.get("/api/posts/{postId}", post.getId())
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("post-read",
            pathParameters(
                parameterWithName("postId").description("게시글 ID")
            ),
            responseFields(
                fieldWithPath("id").description("게시글 ID"),
                fieldWithPath("title").description("게시글 제목"),
                fieldWithPath("content").description("게시글 내용")
            )
        ));
  }

  @Test
  @DisplayName("게시글 작성")
  void 게시글_작성() throws Exception {
    // given
    PostCreate postCreate = PostCreate.builder()
        .title("title")
        .content("content")
        .build();

    // RestDocumentationRequestBuilders 의 HTTP Method 를 사용
    mockMvc.perform(post("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(postCreate)))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("post-create",
            requestFields(
                fieldWithPath("title").description("게시글 제목")
                    .attributes(key("constraint").value("좋은 제목 입력해주세요.")),
                fieldWithPath("content").description("게시글 내용").optional()
            ),
            responseFields(
                fieldWithPath("id").description("게시글 ID"),
                fieldWithPath("title").description("게시글 제목"),
                fieldWithPath("content").description("게시글 내용")
            )
        ));
  }
}
