package com.kbonote.kbofeedservice.domain.player.feed.service;

import com.kbonote.kbofeedservice.common.exception.BadRequestException;
import com.kbonote.kbofeedservice.domain.player.feed.dto.PlayerFeedCursor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerFeedCursorCodec {

    private final ObjectMapper objectMapper;

    public PlayerFeedCursor decode(String cursor) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(cursor);
            JsonNode node = objectMapper.readTree(new String(decodedBytes, StandardCharsets.UTF_8));

            JsonNode scoreNode = node.get("score");
            JsonNode likeCountNode = node.get("like_count");
            JsonNode publishedAtNode = node.get("published_at");
            JsonNode contentIdNode = node.get("content_id");
            if (scoreNode == null || likeCountNode == null || publishedAtNode == null || contentIdNode == null) {
                throw new BadRequestException("Invalid cursor format");
            }

            long score = scoreNode.asLong();
            long likeCount = likeCountNode.asLong();
            LocalDateTime publishedAt = LocalDateTime.parse(publishedAtNode.asText());
            long contentId = contentIdNode.asLong();
            if (contentId <= 0) {
                throw new BadRequestException("Invalid cursor format");
            }

            return new PlayerFeedCursor(score, likeCount, publishedAt, contentId);
        } catch (IllegalArgumentException | DateTimeParseException ex) {
            throw new BadRequestException("Invalid cursor format");
        } catch (BadRequestException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BadRequestException("Invalid cursor format");
        }
    }

    public String encode(PlayerFeedCursor cursor) {
        try {
            String json = objectMapper.createObjectNode()
                    .put("score", cursor.score())
                    .put("like_count", cursor.likeCount())
                    .put("published_at", cursor.publishedAt().toString())
                    .put("content_id", cursor.contentId())
                    .toString();
            return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to encode cursor", ex);
        }
    }
}
