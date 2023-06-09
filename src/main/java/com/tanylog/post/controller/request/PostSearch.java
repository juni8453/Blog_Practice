package com.tanylog.post.controller.request;

import lombok.Builder;
import lombok.Getter;

// @Builder.Default 를 사용하기 위해선 생성자가 아닌 클래스 레벨에서 @Builder 를 사용해야 한다.
@Builder
@Getter
public class PostSearch {

  private static final int MAX_SIZE = 2000;

  // 빌더 패턴을 사용할 떄 특정 필드를 특정 값으로 초기화하여 기본 page, size 설정
  @Builder.Default
  private int page = 1;

  @Builder.Default
  private int size =  20;

  // page 가 0인 경우 최소한 1을 반환하기 위함
  // size 가 너무 커지지 않도록 조치
  public long getOffset() {
    return (long) (Math.max(1, page) - 1) * Math.min(MAX_SIZE, size);
  }
}
