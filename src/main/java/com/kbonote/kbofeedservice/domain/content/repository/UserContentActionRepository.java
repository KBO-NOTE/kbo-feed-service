package com.kbonote.kbofeedservice.domain.content.repository;

import com.kbonote.kbofeedservice.domain.content.entity.UserContentAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserContentActionRepository extends JpaRepository<UserContentAction, Long> {
    @Query("""
            select coalesce(sum(
                case
                    when uca.actionType in (com.kbonote.kbofeedservice.domain.content.entity.ActionType.LIKE_CANCLE,
                                            com.kbonote.kbofeedservice.domain.content.entity.ActionType.COMMENT_DELETE)
                    then -1
                    else 1
                end
            ), 0)
            from UserContentAction uca
            where uca.user.id = :userId
            """)
    long calculateEngagementScore(@Param("userId") Long userId);
}
