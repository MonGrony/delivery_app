package com.sparta.delivery_app.domain.liked.repository;

import com.sparta.delivery_app.domain.user.entity.User;

public interface LikedQueryRepository {

    Long findPersonalLikeAllCount(User user);
}
