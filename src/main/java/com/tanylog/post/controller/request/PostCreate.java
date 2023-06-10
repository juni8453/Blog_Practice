package com.tanylog.post.controller.request;

import com.tanylog.exception.customException.InValidRequest;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostCreate {

  @NotBlank(message = "title 을 입력해주세요.")
  private String title;

  @NotBlank(message = "content 를 입력해주세요.")
  private String content;

  @Builder
  public PostCreate(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public void validate() {
    if (title.contains("바보")) {
      // 어떤 필드가 잘못되었는지 보내주면 된다.
      throw new InValidRequest("title", "제목에 바보를 포함할 수 없습니다.");
    }
  }
}
