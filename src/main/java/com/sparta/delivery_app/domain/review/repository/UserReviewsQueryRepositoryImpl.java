package com.sparta.delivery_app.domain.review.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

import static com.sparta.delivery_app.domain.review.entity.QUserReviews.userReviews;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class UserReviewsQueryRepositoryImpl implements UserReviewsQueryRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findPersonalReviewsAllCount(User user) {
        return jpaQueryFactory
                .select(userReviews.count())
                .from(userReviews)
                .leftJoin(userReviews.user).fetchJoin()
                .where(userIdEq(user.getId()))
                .fetchOne();
    }

    private BooleanExpression userIdEq(Long userId) {
        return nonNull(userId) ? userReviews.user.id.eq(userId) : null;
    }
}
