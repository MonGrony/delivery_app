package com.sparta.delivery_app.domain.store.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.RequiredArgsConstructor;

import static com.sparta.delivery_app.domain.order.entity.QOrder.order;
import static com.sparta.delivery_app.domain.review.entity.QUserReviews.userReviews;
import static com.sparta.delivery_app.domain.store.entity.QStore.store;

@RequiredArgsConstructor
public class StoreQueryRepositoryImpl implements storeQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Store findByStoreByReviewId(Long reviewId) {

        return jpaQueryFactory
                .select(store)
                .from(userReviews)
                .leftJoin(userReviews.order, order).fetchJoin()
                .leftJoin(order.store, store).fetchJoin()
                .where(userReviews.id.eq(reviewId))
                .fetchOne();
    }
}
