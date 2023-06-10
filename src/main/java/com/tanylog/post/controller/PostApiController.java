package com.tanylog.post.controller;

import com.tanylog.post.controller.request.PostCreate;
import com.tanylog.post.controller.request.PostEdit;
import com.tanylog.post.controller.request.PostSearch;
import com.tanylog.post.controller.response.PostRead;
import com.tanylog.post.controller.response.PostReads;
import com.tanylog.post.service.PostService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostApiController {

  private final PostService postService;

  @PostMapping("/api/posts")
  public PostRead post(@RequestBody @Valid PostCreate postCreate) {
    log.info("postRequest={}", postCreate.toString());
    return postService.write(postCreate);
  }

  @GetMapping("/api/posts/{postId}")
  public PostRead read(@PathVariable Long postId) {
    log.info("postId={}", postId);
    return postService.read(postId);
  }

  @GetMapping("/api/posts")
  public PostReads readAll(@ModelAttribute PostSearch postSearch) {
    return postService.readAll(postSearch);
  }

  @PatchMapping("/api/posts/{postId}")
  public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit postEdit) {
    postService.edit(postId, postEdit);
  }

  @DeleteMapping("/api/posts/{postId}")
  public void delete(@PathVariable Long postId) {
    postService.delete(postId);
  }
}
