package com.tanylog.post.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @Lob
  private String content;

  @Builder
  public Post(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public PostEditor.PostEditorBuilder toEditor() {
    return PostEditor.builder()
        .title(title)
        .content(content);
  }

  public void edit(PostEditor postEditor) {
    this.title = postEditor.getTitle();
    this.content = postEditor.getContent();
  }
}
