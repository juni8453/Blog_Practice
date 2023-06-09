package com.tanylog.post.domain;

import lombok.Getter;

@Getter
public class PostEditor {

  private String title;
  private String content;

  public PostEditor(String title, String content) {
    this.title = title;
    this.content = content;
  }

  // 빌더 직접 구현 (classes 클래스의 구현된 빌더 가져오면 됨)
  public static PostEditor.PostEditorBuilder builder() {
    return new PostEditor.PostEditorBuilder();
  }

  public static class PostEditorBuilder {

    private String title;
    private String content;

    PostEditorBuilder() {
    }

    public PostEditor.PostEditorBuilder title(final String title) {
      if (title != null) {
        this.title = title;
      }

      return this;
    }

    public PostEditor.PostEditorBuilder content(final String content) {
      if (content != null) {
        this.content = content;

      }
      return this;
    }

    public PostEditor build() {
      return new PostEditor(this.title, this.content);
    }

    public String toString() {
      return "PostEditor.PostEditorBuilder(title=" + this.title + ", content=" + this.content + ")";
    }
  }
}
