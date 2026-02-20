package com.choyeji.kbofeedservice.domain.content.comment.service;

import com.choyeji.kbofeedservice.domain.content.comment.dto.CommentCreateRequest;
import com.choyeji.kbofeedservice.domain.content.comment.dto.CommentCreateResponse;

public interface ContentCommentCommandService {
    CommentCreateResponse createComment(Long contentId, Long userId, CommentCreateRequest request);
}
