package com.kbonote.kbofeedservice.domain.user.profile;

import java.util.Map;
import java.util.Set;

public interface UserProfileClient {

    Map<Long, UserProfileInfo> getProfiles(Set<Long> userIds);
}
