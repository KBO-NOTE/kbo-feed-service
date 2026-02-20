package com.choyeji.kbofeedservice.domain.content.like.controller;

import com.choyeji.kbofeedservice.common.exception.UnauthorizedException;
import com.choyeji.kbofeedservice.domain.content.like.dto.ContentLikeToggleResponse;
import com.choyeji.kbofeedservice.domain.content.like.service.ContentLikeCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Content Like", description = "Content like API")
@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentLikeController {

    private final ContentLikeCommandService contentLikeCommandService;

    @Operation(summary = "Toggle content like")
    @PostMapping("/{content_id}/like")
    public ContentLikeToggleResponse toggleLike(
            @PathVariable("content_id") Long contentId,
            @RequestHeader(value = "X-User-ID", required = false) Long userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole
    ) {
        validateAuthHeaders(userId, userRole);
        return contentLikeCommandService.toggleLike(contentId, userId);
    }

    private void validateAuthHeaders(Long userId, String userRole) {
        if (userId == null || !StringUtils.hasText(userRole)) {
            throw new UnauthorizedException("인증이 필요합니다.");
        }
    }
}
