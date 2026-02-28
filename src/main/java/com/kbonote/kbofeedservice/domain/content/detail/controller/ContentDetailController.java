package com.kbonote.kbofeedservice.domain.content.detail.controller;

import com.kbonote.kbofeedservice.common.auth.CurrentUser;
import com.kbonote.kbofeedservice.common.auth.CurrentUserParam;
import com.kbonote.kbofeedservice.domain.content.detail.dto.ContentDetailResponse;
import com.kbonote.kbofeedservice.domain.content.detail.service.ContentDetailQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Content", description = "Content API")
@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentDetailController {

    private final ContentDetailQueryService contentDetailQueryService;

    @Operation(summary = "Get content detail metadata")
    @GetMapping("/{content_id}")
    public ContentDetailResponse getContentDetail(
            @PathVariable("content_id") Long contentId,
            @Parameter(hidden = true)
            @CurrentUserParam CurrentUser currentUser
    ) {
        return contentDetailQueryService.getContentDetail(contentId, currentUser.userId());
    }
}
