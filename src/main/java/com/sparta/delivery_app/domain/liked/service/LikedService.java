package com.sparta.delivery_app.domain.liked.service;

import com.sparta.delivery_app.common.exception.errorcode.LikedErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.liked.LikedDuplicatedException;
import com.sparta.delivery_app.common.globalcustomexception.liked.LikedNotFoundException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.liked.adapter.LikedAdapter;
import com.sparta.delivery_app.domain.liked.entity.Liked;
import com.sparta.delivery_app.domain.store.adapter.StoreAdapter;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikedService {

    private final LikedAdapter likedAdapter;
    private final StoreAdapter storeAdapter;
    private final UserAdapter userAdapter;

    public void addLiked(AuthenticationUser user, final Long storeId) {

        Store store = storeAdapter.queryStoreById(storeId);
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        if (likedAdapter.existsByStoreAndUser(store, findUser)) {
            throw new LikedDuplicatedException(LikedErrorCode.LIKED_ALREADY_REGISTERED_ERROR);
        }

        Liked liked = new Liked(store, findUser);
        likedAdapter.saveLiked(liked);
    }

    @Transactional
    public void deleteLiked(AuthenticationUser user, final Long storeId) {
        Store store = storeAdapter.queryStoreById(storeId);
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        if (!likedAdapter.existsByStoreAndUser(store, findUser)) {
            throw new LikedNotFoundException(LikedErrorCode.LIKED_UNREGISTERED_ERROR);
        }

        Liked findLiked = likedAdapter.queryLikedByStoreId(storeId);
        likedAdapter.deleteLiked(findLiked);
    }
}
