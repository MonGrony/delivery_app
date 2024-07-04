package com.sparta.delivery_app.domain.store.repository;

import com.sparta.delivery_app.domain.store.entity.Store;

public interface storeQueryRepository {

    Store findByStoreByReviewId(Long reviewId);
}
