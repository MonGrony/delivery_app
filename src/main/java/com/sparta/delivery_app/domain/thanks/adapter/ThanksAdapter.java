package com.sparta.delivery_app.domain.thanks.adapter;

import com.sparta.delivery_app.domain.thanks.entity.Thanks;
import com.sparta.delivery_app.domain.thanks.repository.TanksRepository;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ThanksAdapter {

    private final TanksRepository thanksRepository;

    public Page<Thanks> queryUserThanksPage(User user, Pageable pageable) {
        return thanksRepository.findPersonalThanksAll(user, pageable);
    }

    public Long queryAllThanksCountByUser(User user) {
        return thanksRepository.findPersonalThanksAllCount(user);
    }
}
