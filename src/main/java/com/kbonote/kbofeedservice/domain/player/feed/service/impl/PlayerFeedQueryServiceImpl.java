package com.kbonote.kbofeedservice.domain.player.feed.service.impl;

import com.kbonote.kbofeedservice.common.exception.BadRequestException;
import com.kbonote.kbofeedservice.domain.content.repository.UserContentActionRepository;
import com.kbonote.kbofeedservice.domain.player.exception.PlayerNotFoundException;
import com.kbonote.kbofeedservice.domain.player.feed.dto.PlayerFeedCursor;
import com.kbonote.kbofeedservice.domain.player.feed.dto.PlayerFeedItemResponse;
import com.kbonote.kbofeedservice.domain.player.feed.dto.PlayerFeedQuery;
import com.kbonote.kbofeedservice.domain.player.feed.dto.PlayerFeedResponse;
import com.kbonote.kbofeedservice.domain.player.feed.repository.PlayerFeedQueryRepository;
import com.kbonote.kbofeedservice.domain.player.feed.repository.PlayerFeedRow;
import com.kbonote.kbofeedservice.domain.player.feed.service.PlayerFeedCursorCodec;
import com.kbonote.kbofeedservice.domain.player.feed.service.PlayerFeedQueryService;
import com.kbonote.kbofeedservice.domain.player.repository.PlayerRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlayerFeedQueryServiceImpl implements PlayerFeedQueryService {

    private static final int DEFAULT_SIZE = 5;
    private static final int MAX_SIZE = 50;
    private static final int PERSONALIZATION_THRESHOLD = 5;

    private final PlayerRepository playerRepository;
    private final UserContentActionRepository userContentActionRepository;
    private final PlayerFeedQueryRepository playerFeedQueryRepository;
    private final PlayerFeedCursorCodec playerFeedCursorCodec;

    @Override
    public PlayerFeedResponse getPlayerFeeds(Long playerId, Long userId, PlayerFeedQuery query) {
        if (!playerRepository.existsById(playerId)) {
            throw new PlayerNotFoundException("선수를 찾을 수 없습니다.");
        }

        int pageSize = normalizeSize(query.size());
        PlayerFeedCursor cursor = decodeCursor(query.cursor());
        boolean personalized = shouldPersonalize(userId);
        LocalDateTime cutoff = LocalDateTime.now().minusMonths(1);

        List<PlayerFeedRow> rows = personalized
                ? playerFeedQueryRepository.findPersonalizedFeeds(playerId, userId, cursor, pageSize + 1, cutoff)
                : playerFeedQueryRepository.findDefaultFeeds(playerId, userId, cursor, pageSize + 1, cutoff);

        boolean hasNext = rows.size() > pageSize;
        List<PlayerFeedRow> pageRows = hasNext ? rows.subList(0, pageSize) : rows;
        String nextCursor = buildNextCursor(hasNext, pageRows);

        List<PlayerFeedItemResponse> items = pageRows.stream()
                .map(row -> new PlayerFeedItemResponse(
                        row.contentId(),
                        row.title(),
                        row.platform(),
                        row.representativeImageUrl(),
                        row.liked(),
                        row.likeCount(),
                        row.commentCount(),
                        row.publishedAt(),
                        row.score()
                ))
                .toList();

        return new PlayerFeedResponse(items, hasNext, nextCursor);
    }

    private boolean shouldPersonalize(Long userId) {
        long engagementScore = userContentActionRepository.calculateEngagementScore(userId);
        return engagementScore >= PERSONALIZATION_THRESHOLD;
    }

    private int normalizeSize(Integer size) {
        if (size == null) {
            return DEFAULT_SIZE;
        }
        if (size < 1 || size > MAX_SIZE) {
            throw new BadRequestException("size must be between 1 and 50");
        }
        return size;
    }

    private PlayerFeedCursor decodeCursor(String cursor) {
        if (cursor == null || cursor.isBlank()) {
            return null;
        }
        return playerFeedCursorCodec.decode(cursor);
    }

    private String buildNextCursor(boolean hasNext, List<PlayerFeedRow> rows) {
        if (!hasNext || rows.isEmpty()) {
            return null;
        }
        PlayerFeedRow last = rows.get(rows.size() - 1);
        return playerFeedCursorCodec.encode(new PlayerFeedCursor(
                last.score(),
                last.likeCount(),
                last.publishedAt(),
                last.contentId()
        ));
    }
}
