package com.choyeji.kbofeedservice.domain.content.comment.controller;

import com.choyeji.kbofeedservice.domain.content.comment.dto.CommentListResponse;
import com.choyeji.kbofeedservice.domain.content.comment.dto.CommentListQuery;
import com.choyeji.kbofeedservice.domain.content.comment.service.ContentCommentQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Content Comment", description = "Content comment API")
@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentCommentController {

    private final ContentCommentQueryService contentCommentQueryService;

    @Operation(summary = "Get content comments with cursor-based infinite scroll")
    @GetMapping("/{content_id}/comments")
    public CommentListResponse getComments(
            @PathVariable("content_id") Long contentId,
            @ModelAttribute CommentListQuery query,
            @RequestHeader("X-User-ID") Long userId,
            @RequestHeader("X-User-Role") String userRole
    ) {
        return contentCommentQueryService.getComments(contentId, query);
    }
}
