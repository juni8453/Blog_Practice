package com.tanylog.post.repository;

import static com.tanylog.post.domain.QPost.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tanylog.post.controller.request.PostSearch;
import com.tanylog.post.domain.Post;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Post> readAll(PostSearch postSearch) {
    return jpaQueryFactory.selectFrom(post)
        .limit(postSearch.getSize())
        .offset(postSearch.getOffset())
        .orderBy(post.id.desc())
        .fetch();
  }
}
