package com.sparta.delivery_app.domain.review.repository;

import com.sparta.delivery_app.domain.user.entity.User;

public interface UserReviewsQueryRepository {

    Long findPersonalReviewsAllCount(User user);
}
