package com.kbonote.kbofeedservice.domain.content.comment.controller;

import com.kbonote.kbofeedservice.domain.content.comment.dto.CommentCreateRequest;
import com.kbonote.kbofeedservice.domain.content.comment.dto.CommentCreateResponse;
import com.kbonote.kbofeedservice.domain.content.comment.dto.CommentListQuery;
import com.kbonote.kbofeedservice.domain.content.comment.dto.CommentListResponse;
import com.kbonote.kbofeedservice.domain.content.comment.service.ContentCommentCommandService;
import com.kbonote.kbofeedservice.domain.content.comment.service.ContentCommentQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Content Comment", description = "Content comment API")
@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentCommentController {

    private final ContentCommentQueryService contentCommentQueryService;
    private final ContentCommentCommandService contentCommentCommandService;

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

    @Operation(summary = "Create comment on content")
    @PostMapping("/{content_id}/comments")
    public ResponseEntity<CommentCreateResponse> createComment(
            @PathVariable("content_id") Long contentId,
            @RequestHeader("X-User-ID") Long userId,
            @RequestHeader("X-User-Role") String userRole,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        CommentCreateResponse response = contentCommentCommandService.createComment(contentId, userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
