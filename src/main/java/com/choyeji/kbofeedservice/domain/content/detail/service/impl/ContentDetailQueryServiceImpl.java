package com.choyeji.kbofeedservice.domain.content.detail.service.impl;

import com.choyeji.kbofeedservice.common.exception.ResourceNotFoundException;
import com.choyeji.kbofeedservice.domain.content.detail.dto.ContentDetailResponse;
import com.choyeji.kbofeedservice.domain.content.detail.service.ContentDetailQueryService;
import com.choyeji.kbofeedservice.domain.content.entity.Content;
import com.choyeji.kbofeedservice.domain.content.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentDetailQueryServiceImpl implements ContentDetailQueryService {

    private final ContentRepository contentRepository;

    @Override
    public ContentDetailResponse getContentDetail(Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found"));

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
