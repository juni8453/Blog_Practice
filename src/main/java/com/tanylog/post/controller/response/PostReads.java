package com.tanylog.post.controller.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostReads {

  private final List<PostRead> postReads;

  @Builder
  public PostReads(List<PostRead> postReads) {
    this.postReads = postReads;
  }
}
