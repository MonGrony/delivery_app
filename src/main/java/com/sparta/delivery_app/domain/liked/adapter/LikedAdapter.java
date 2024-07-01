package com.sparta.delivery_app.domain.liked.adapter;

import com.sparta.delivery_app.common.exception.errorcode.LikedErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.liked.LikedNotFoundException;
import com.sparta.delivery_app.domain.liked.entity.Liked;
import com.sparta.delivery_app.domain.liked.repository.LikedRepository;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikedAdapter {

    private final LikedRepository likedRepository;

    public void saveLiked(Liked liked) {
        likedRepository.save(liked);
    }

    public void deleteLiked(Liked liked) {
        likedRepository.delete(liked);
    }

    public Liked queryLikedByStoreId(Long storeId) {
        return likedRepository.findByStoreId(storeId).orElseThrow(() ->
                new LikedNotFoundException(LikedErrorCode.LIKED_UNREGISTERED_ERROR));
    }

    public boolean existsByStoreAndUser(Store store, User user) {
        return likedRepository.existsByStoreAndUser(store, user);
    }
}
