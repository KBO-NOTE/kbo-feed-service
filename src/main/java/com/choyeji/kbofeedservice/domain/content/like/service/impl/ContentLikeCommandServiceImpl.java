package com.choyeji.kbofeedservice.domain.content.like.service.impl;

import com.choyeji.kbofeedservice.common.exception.UnauthorizedException;
import com.choyeji.kbofeedservice.domain.content.entity.Content;
import com.choyeji.kbofeedservice.domain.content.entity.ContentLike;
import com.choyeji.kbofeedservice.domain.content.like.dto.ContentLikeToggleResponse;
import com.choyeji.kbofeedservice.domain.content.like.exception.ArticleNotFoundException;
import com.choyeji.kbofeedservice.domain.content.like.service.ContentLikeCommandService;
import com.choyeji.kbofeedservice.domain.content.repository.ContentLikeRepository;
import com.choyeji.kbofeedservice.domain.content.repository.ContentRepository;
import com.choyeji.kbofeedservice.domain.user.entity.User;
import com.choyeji.kbofeedservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentLikeCommandServiceImpl implements ContentLikeCommandService {

    private final ContentRepository contentRepository;
    private final ContentLikeRepository contentLikeRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ContentLikeToggleResponse toggleLike(Long contentId, Long userId) {
        validateUser(userId);

        Content content = contentRepository.findByIdForUpdate(contentId)
                .orElseThrow(() -> new ArticleNotFoundException("게시글을 찾을 수 없습니다."));

        return contentLikeRepository.findByContentIdAndUserId(contentId, userId)
                .map(existingLike -> toggleExistingLike(content, existingLike))
                .orElseGet(() -> createLike(content, userId));
    }

    private ContentLikeToggleResponse createLike(Content content, Long userId) {
        User user = userRepository.getReferenceById(userId);
        contentLikeRepository.save(ContentLike.create(content, user));
        content.increaseLikeCount();
        return new ContentLikeToggleResponse(content.getId(), true, content.getLikeCount());
    }

    private ContentLikeToggleResponse cancelLike(Content content, ContentLike existingLike) {
        existingLike.markCanceled();
        content.decreaseLikeCount();
        return new ContentLikeToggleResponse(content.getId(), false, content.getLikeCount());
    }

    private ContentLikeToggleResponse toggleExistingLike(Content content, ContentLike existingLike) {
        if (existingLike.isLiked()) {
            return cancelLike(content, existingLike);
        }
        existingLike.markLiked();
        content.increaseLikeCount();
        return new ContentLikeToggleResponse(content.getId(), true, content.getLikeCount());
    }

    private void validateUser(Long userId) {
        if (userId == null || !userRepository.existsById(userId)) {
            throw new UnauthorizedException("인증이 필요합니다.");
        }
    }
}
