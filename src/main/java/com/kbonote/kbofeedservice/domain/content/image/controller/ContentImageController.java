package com.kbonote.kbofeedservice.domain.content.image.controller;

import com.kbonote.kbofeedservice.domain.content.image.dto.ContentImageListResponse;
import com.kbonote.kbofeedservice.domain.content.image.service.ContentImageQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Content Image", description = "Content image API")
@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentImageController {

    private final ContentImageQueryService contentImageQueryService;

    @Operation(summary = "Get content image list")
    @GetMapping("/{content_id}/images")
    public ContentImageListResponse getContentImages(
            @PathVariable("content_id") Long contentId,
            @RequestHeader("X-User-ID") Long userId,
            @RequestHeader("X-User-Role") String userRole
    ) {
        return contentImageQueryService.getContentImages(contentId);
    }
}