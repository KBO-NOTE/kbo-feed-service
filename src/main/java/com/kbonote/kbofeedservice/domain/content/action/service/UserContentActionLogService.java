package com.kbonote.kbofeedservice.domain.content.action.service;

import com.kbonote.kbofeedservice.domain.content.entity.ActionType;
import com.kbonote.kbofeedservice.domain.content.entity.Content;

public interface UserContentActionLogService {
    void log(Content content, Long userId, ActionType actionType);
}
