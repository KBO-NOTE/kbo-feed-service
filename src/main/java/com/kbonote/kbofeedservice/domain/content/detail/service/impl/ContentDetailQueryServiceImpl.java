package com.kbonote.kbofeedservice.domain.content.detail.service.impl;

import com.kbonote.kbofeedservice.common.exception.ResourceNotFoundException;
import com.kbonote.kbofeedservice.domain.content.action.service.UserContentActionLogService;
import com.kbonote.kbofeedservice.domain.content.detail.dto.ContentDetailResponse;
import com.kbonote.kbofeedservice.domain.content.detail.service.ContentDetailQueryService;
import com.kbonote.kbofeedservice.domain.content.entity.ActionType;
import com.kbonote.kbofeedservice.domain.content.entity.Content;
import com.kbonote.kbofeedservice.domain.content.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentDetailQueryServiceImpl implements ContentDetailQueryService {

    private final ContentRepository contentRepository;
    private final UserContentActionLogService userContentActionLogService;

    @Override
    public ContentDetailResponse getContentDetail(Long contentId, Long userId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found"));

        userContentActionLogService.log(content, userId, ActionType.VIEW);

        return new ContentDetailResponse(
                content.getId(),
                content.getTitle(),
                resolveArticleUrlOrigin(content),
                content.getRepresentativeImgUrl(),
                content.getImageCount(),
                content.getLikeCount(),
                content.getCommentCount(),
                content.getPublishedAt()
        );
    }

    private String resolveArticleUrlOrigin(Content content) {
        if (!StringUtils.hasText(content.getArticleUrlOrigin())) {
            return "";
        }
        return content.getArticleUrlOrigin();
    }
}