package com.kbonote.kbofeedservice.domain.content.comment.service.impl;

import com.kbonote.kbofeedservice.common.exception.BadRequestException;
import com.kbonote.kbofeedservice.common.exception.ResourceNotFoundException;
import com.kbonote.kbofeedservice.domain.content.comment.dto.CommentCursor;
import com.kbonote.kbofeedservice.domain.content.comment.dto.CommentItemResponse;
import com.kbonote.kbofeedservice.domain.content.comment.dto.CommentListResponse;
import com.kbonote.kbofeedservice.domain.content.comment.dto.CommentListQuery;
import com.kbonote.kbofeedservice.domain.content.comment.dto.CommentRow;
import com.kbonote.kbofeedservice.domain.content.comment.dto.CommentSortType;
import com.kbonote.kbofeedservice.domain.content.comment.service.CommentCursorCodec;
import com.kbonote.kbofeedservice.domain.content.comment.service.ContentCommentQueryService;
import com.kbonote.kbofeedservice.domain.content.repository.ContentCommentRepository;
import com.kbonote.kbofeedservice.domain.content.repository.ContentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentCommentQueryServiceImpl implements ContentCommentQueryService {

    private static final int DEFAULT_SIZE = 20;
    private static final int MAX_SIZE = 100;

    private final ContentRepository contentRepository;
    private final ContentCommentRepository contentCommentRepository;
    private final CommentCursorCodec commentCursorCodec;

    @Override
    public CommentListResponse getComments(Long contentId, CommentListQuery query) {
        if (!contentRepository.existsById(contentId)) {
            throw new ResourceNotFoundException("Content not found");
        }

        int pageSize = normalizeSize(query.size());
        CommentSortType sortType = CommentSortType.from(query.sort());
        CommentCursor decodedCursor = query.cursor() == null || query.cursor().isBlank()
                ? null
                : commentCursorCodec.decode(query.cursor());
        List<CommentRow> rows = loadRows(contentId, sortType, decodedCursor, pageSize + 1);

        boolean hasNext = rows.size() > pageSize;
        List<CommentRow> currentPageRows = hasNext ? rows.subList(0, pageSize) : rows;

        String nextCursor = null;
        if (hasNext && !currentPageRows.isEmpty()) {
            CommentRow last = currentPageRows.get(currentPageRows.size() - 1);
            nextCursor = commentCursorCodec.encode(new CommentCursor(last.createdAt(), last.id()));
        }

        List<CommentItemResponse> comments = currentPageRows.stream()
                .map(row -> new CommentItemResponse(
                        row.id(),
                        row.userId(),
                        row.nickname(),
                        row.content(),
                        row.createdAt()
                ))
                .toList();

        return new CommentListResponse(comments, hasNext, nextCursor);
    }

    private int normalizeSize(Integer size) {
        if (size == null) {
            return DEFAULT_SIZE;
        }
        if (size < 1 || size > MAX_SIZE) {
            throw new BadRequestException("size must be between 1 and 100");
        }
        return size;
    }

    private List<CommentRow> loadRows(Long contentId, CommentSortType sortType, CommentCursor cursor, int limit) {
        PageRequest pageable = PageRequest.of(0, limit);

        if (sortType == CommentSortType.LATEST) {
            if (cursor == null) {
                return contentCommentRepository.findLatestFirstPage(contentId, pageable);
            }
            return contentCommentRepository.findLatestNextPage(
                    contentId,
                    cursor.createdAt(),
                    cursor.id(),
                    pageable
            );
        }

        if (cursor == null) {
            return contentCommentRepository.findOldestFirstPage(contentId, pageable);
        }
        return contentCommentRepository.findOldestNextPage(
                contentId,
                cursor.createdAt(),
                cursor.id(),
                pageable
        );
    }
}