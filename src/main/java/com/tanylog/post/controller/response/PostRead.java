package com.tanylog.post.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostRead {

  private final String title;
  private final String content;

  @Builder
  public PostRead(String title, String content) {
    this.title = title;
    this.content = content;
  }
}
