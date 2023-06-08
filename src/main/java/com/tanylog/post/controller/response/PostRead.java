package com.tanylog.post.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostRead {

  private final Long id;
  private final String title;
  private final String content;

  @Builder
  public PostRead(Long id, String title, String content) {
    this.id = id;
    this.title = title;
    this.content = content;
  }
}
