package com.kbonote.kbofeedservice.domain.content.comment.service.impl;

import com.kbonote.kbofeedservice.common.exception.UnauthorizedException;
import com.kbonote.kbofeedservice.domain.content.action.service.UserContentActionLogService;
import com.kbonote.kbofeedservice.domain.content.comment.dto.CommentCreateRequest;
import com.kbonote.kbofeedservice.domain.content.comment.dto.CommentCreateResponse;
import com.kbonote.kbofeedservice.domain.content.comment.exception.InvalidContentException;
import com.kbonote.kbofeedservice.domain.content.comment.service.ContentCommentCommandService;
import com.kbonote.kbofeedservice.domain.content.entity.ActionType;
import com.kbonote.kbofeedservice.domain.content.entity.Content;
import com.kbonote.kbofeedservice.domain.content.entity.ContentComment;
import com.kbonote.kbofeedservice.domain.content.like.exception.ArticleNotFoundException;
import com.kbonote.kbofeedservice.domain.content.repository.ContentCommentRepository;
import com.kbonote.kbofeedservice.domain.content.repository.ContentRepository;
import com.kbonote.kbofeedservice.domain.user.entity.User;
import com.kbonote.kbofeedservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ContentCommentCommandServiceImpl implements ContentCommentCommandService {

    private final ContentRepository contentRepository;
    private final ContentCommentRepository contentCommentRepository;
    private final UserRepository userRepository;
    private final UserContentActionLogService userContentActionLogService;

    @Override
    @Transactional
    public CommentCreateResponse createComment(Long contentId, Long userId, CommentCreateRequest request) {
        validateUser(userId);
        String normalizedComment = normalizeComment(request.comment());

        Content content = contentRepository.findByIdForUpdate(contentId)
                .orElseThrow(() -> new ArticleNotFoundException("게시글을 찾을 수 없습니다."));
        User user = userRepository.getReferenceById(userId);

        ContentComment saved = contentCommentRepository.save(ContentComment.create(content, user, normalizedComment));
        content.increaseCommentCount();
        userContentActionLogService.log(content, userId, ActionType.COMMENT);

        return new CommentCreateResponse(saved.getId());
    }

    private void validateUser(Long userId) {
        if (userId == null || !userRepository.existsById(userId)) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
    }

    private String normalizeComment(String comment) {
        if (!StringUtils.hasText(comment)) {
            throw new InvalidContentException("댓글 내용은 비어 있을 수 없습니다.");
        }

        String trimmed = comment.trim();
        if (trimmed.length() > 1000) {
            throw new InvalidContentException("댓글은 1000자 이하여야 합니다.");
        }
        return trimmed;
    }
}
