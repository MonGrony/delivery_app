package com.sparta.delivery_app.domain.thanks.repository;

import com.sparta.delivery_app.domain.thanks.entity.Thanks;
import com.sparta.delivery_app.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ThanksQueryRepository {

    Page<Thanks> findPersonalThanksAll(User user, Pageable pageable);
}
