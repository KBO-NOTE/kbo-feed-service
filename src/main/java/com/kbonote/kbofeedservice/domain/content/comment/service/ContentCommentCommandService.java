package com.kbonote.kbofeedservice.domain.content.comment.service;

import com.kbonote.kbofeedservice.domain.content.comment.dto.CommentCreateRequest;
import com.kbonote.kbofeedservice.domain.content.comment.dto.CommentCreateResponse;

public interface ContentCommentCommandService {
    CommentCreateResponse createComment(Long contentId, Long userId, CommentCreateRequest request);
}