package com.kbonote.kbofeedservice.domain.content.comment.service;

import com.kbonote.kbofeedservice.common.exception.BadRequestException;
import com.kbonote.kbofeedservice.domain.content.comment.dto.CommentCursor;
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
public class CommentCursorCodec {

    private final ObjectMapper objectMapper;

    public CommentCursor decode(String cursor) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(cursor);
            JsonNode node = objectMapper.readTree(new String(decodedBytes, StandardCharsets.UTF_8));

            JsonNode createdAtNode = node.get("createdAt");
            JsonNode idNode = node.get("id");
            if (createdAtNode == null || idNode == null) {
                throw new BadRequestException("Invalid cursor format");
            }

            LocalDateTime createdAt = LocalDateTime.parse(createdAtNode.asText());
            long id = idNode.asLong();
            if (id <= 0) {
                throw new BadRequestException("Invalid cursor format");
            }

            return new CommentCursor(createdAt, id);
        } catch (IllegalArgumentException | DateTimeParseException ex) {
            throw new BadRequestException("Invalid cursor format");
        } catch (BadRequestException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BadRequestException("Invalid cursor format");
        }
    }

    public String encode(CommentCursor cursor) {
        try {
            String json = objectMapper.writeValueAsString(cursor);
            return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to encode cursor", ex);
        }
    }
}