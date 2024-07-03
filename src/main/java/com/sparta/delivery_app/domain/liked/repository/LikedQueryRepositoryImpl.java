package com.sparta.delivery_app.domain.liked.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

import static com.sparta.delivery_app.domain.review.entity.QUserReviews.userReviews;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class LikedQueryRepositoryImpl implements LikedQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findPersonalLikeAllCount(User user) {
        return jpaQueryFactory
                .selectDistinct( userReviews.count())
                .from(userReviews)
                .leftJoin(userReviews.user).fetchJoin()
                .where(
                        userIdEq(user.getId()),
                        userReviews.reviewStatus.eq(ReviewStatus.ENABLE)
                )
                .fetchOne();
    }

    private BooleanExpression userIdEq(Long userId) {
        return nonNull(userId) ? userReviews.user.id.eq(userId) : null;
    }
}
