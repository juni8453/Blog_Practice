package com.tanylog.post.service;

import com.tanylog.exception.PostNotFound;
import com.tanylog.post.controller.request.PostCreate;
import com.tanylog.post.controller.request.PostEdit;
import com.tanylog.post.controller.request.PostSearch;
import com.tanylog.post.controller.response.PostRead;
import com.tanylog.post.controller.response.PostReads;
import com.tanylog.post.domain.Post;
import com.tanylog.post.domain.PostEditor;
import com.tanylog.post.domain.PostEditor.PostEditorBuilder;
import com.tanylog.post.repository.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

  private final PostRepository postRepository;

  public PostRead write(PostCreate createPost) {
    Post post = Post.builder()
        .title(createPost.getTitle())
        .content(createPost.getContent())
        .build();

    Post savePost = postRepository.save(post);

    return PostRead.builder()
        .id(savePost.getId())
        .title(savePost.getTitle())
        .content(savePost.getContent())
        .build();
  }

  public PostRead read(Long postId) {
    Post findPost = postRepository.findById(postId)
        .orElseThrow(PostNotFound::new);

    return PostRead.builder()
        .id(findPost.getId())
        .title(findPost.getTitle())
        .content(findPost.getContent())
        .build();
  }

  public PostReads readAll(PostSearch postSearch) {
    List<PostRead> postReads = postRepository.readAll(postSearch).stream()
        .map(pagePost -> PostRead.builder()
            .id(pagePost.getId())
            .title(pagePost.getTitle())
            .content(pagePost.getContent())
            .build())
        .collect(Collectors.toList());

    return PostReads.builder()
        .postReads(postReads)
        .build();
  }

  @Transactional
  public void edit(Long postId, PostEdit postEdit) {
    Post findPost = postRepository.findById(postId)
        .orElseThrow(PostNotFound::new);

    // 결론) 최초 builder 호출 시점에 null 체크 해줘야한다.
    PostEditorBuilder postEditorBuilder = findPost.toEditor();

    PostEditor postEditor = postEditorBuilder
        .title(postEdit.getTitle())
        .content(postEdit.getContent())
        .build();

    findPost.edit(postEditor);
  }

  @Transactional
  public void delete(Long postId) {
    Post findPost = postRepository.findById(postId)
        .orElseThrow(PostNotFound::new);

    postRepository.delete(findPost);
  }
}
