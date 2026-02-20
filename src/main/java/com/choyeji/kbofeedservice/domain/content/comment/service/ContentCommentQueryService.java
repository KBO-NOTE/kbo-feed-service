package com.choyeji.kbofeedservice.domain.content.comment.service;

import com.choyeji.kbofeedservice.domain.content.comment.dto.CommentListResponse;
import com.choyeji.kbofeedservice.domain.content.comment.dto.CommentListQuery;

public interface ContentCommentQueryService {
    CommentListResponse getComments(Long contentId, CommentListQuery query);
}
