package com.tanylog.post.controller;

import com.tanylog.post.controller.request.PostCreate;
import com.tanylog.post.service.PostService;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostApiController {

  private final PostService postService;

  @PostMapping("/api/posts")
  public Map<String, String> post(@RequestBody @Valid PostCreate postCreate) {
    log.info("postRequest={}", postCreate.toString());

    return Map.of();
  }
}
