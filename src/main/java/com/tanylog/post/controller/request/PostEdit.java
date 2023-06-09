package com.tanylog.post.controller.request;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostEdit {

  @NotBlank(message = "title 을 입력해주세요.")
  private String title;

  @NotBlank(message = "content 를 입력해주세요.")
  private String content;

  @Builder
  public PostEdit(String title, String content) {
    this.title = title;
    this.content = content;
  }
}
