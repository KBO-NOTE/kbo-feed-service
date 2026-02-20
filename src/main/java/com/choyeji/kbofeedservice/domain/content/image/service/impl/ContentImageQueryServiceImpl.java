package com.choyeji.kbofeedservice.domain.content.image.service.impl;

import com.choyeji.kbofeedservice.common.exception.ResourceNotFoundException;
import com.choyeji.kbofeedservice.domain.content.image.dto.ContentImageItemResponse;
import com.choyeji.kbofeedservice.domain.content.image.dto.ContentImageListResponse;
import com.choyeji.kbofeedservice.domain.content.image.service.ContentImageQueryService;
import com.choyeji.kbofeedservice.domain.content.repository.ContentRepository;
import com.choyeji.kbofeedservice.domain.content.repository.ImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentImageQueryServiceImpl implements ContentImageQueryService {

    private final ContentRepository contentRepository;
    private final ImageRepository imageRepository;

    @Override
    public ContentImageListResponse getContentImages(Long contentId) {
        if (!contentRepository.existsById(contentId)) {
            throw new ResourceNotFoundException("Content not found");
        }

        List<ContentImageItemResponse> images = imageRepository.findImagesByContentId(contentId);
        return new ContentImageListResponse(images);
    }
}
