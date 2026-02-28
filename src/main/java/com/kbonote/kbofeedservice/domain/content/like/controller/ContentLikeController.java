package com.kbonote.kbofeedservice.domain.content.like.controller;

import com.kbonote.kbofeedservice.common.auth.CurrentUser;
import com.kbonote.kbofeedservice.common.auth.CurrentUserParam;
import com.kbonote.kbofeedservice.domain.content.like.dto.ContentLikeToggleResponse;
import com.kbonote.kbofeedservice.domain.content.like.service.ContentLikeCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
            @Parameter(hidden = true)
            @CurrentUserParam CurrentUser currentUser
    ) {
        return contentLikeCommandService.toggleLike(contentId, currentUser.userId());
    }
}
