package com.tanylog.post.repository;

import com.tanylog.post.controller.request.PostSearch;
import com.tanylog.post.domain.Post;
import java.util.List;

public interface PostCustomRepository {

  List<Post> readAll(PostSearch postSearch);
}
