package com.kbonote.kbofeedservice.domain.content.action.service.impl;

import com.kbonote.kbofeedservice.domain.content.action.service.UserContentActionLogService;
import com.kbonote.kbofeedservice.domain.content.entity.ActionType;
import com.kbonote.kbofeedservice.domain.content.entity.Content;
import com.kbonote.kbofeedservice.domain.content.entity.UserContentAction;
import com.kbonote.kbofeedservice.domain.content.repository.UserContentActionRepository;
import com.kbonote.kbofeedservice.domain.user.entity.User;
import com.kbonote.kbofeedservice.domain.user.repository.UserRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserContentActionLogServiceImpl implements UserContentActionLogService {

    private final UserContentActionRepository userContentActionRepository;
    private final UserRepository userRepository;
    private final Counter actionLogFailureCounter;

    public UserContentActionLogServiceImpl(
            UserContentActionRepository userContentActionRepository,
            UserRepository userRepository,
            MeterRegistry meterRegistry
    ) {
        this.userContentActionRepository = userContentActionRepository;
        this.userRepository = userRepository;
        this.actionLogFailureCounter = Counter.builder("user_content_action.log.failure")
                .description("Number of failures while writing user_content_action logs")
                .register(meterRegistry);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(Content content, Long userId, ActionType actionType) {
        try {
            User user = userRepository.getReferenceById(userId);
            userContentActionRepository.save(UserContentAction.create(content, user, actionType));
        } catch (Exception ex) {
            // Action log failure should not break main user flow.
            actionLogFailureCounter.increment();
            log.error("USER_CONTENT_ACTION_LOG_WRITE_FAILED userId={}, contentId={}, actionType={}",
                    userId, content.getId(), actionType, ex);
        }
    }
}
