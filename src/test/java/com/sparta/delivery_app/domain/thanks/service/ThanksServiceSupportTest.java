package com.sparta.delivery_app.domain.thanks.service;

import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.review.dto.request.UserReviewAddRequestDto;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.store.dto.request.RegisterStoreRequestDto;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.dto.request.ConsumersSignupRequestDto;
import com.sparta.delivery_app.domain.user.dto.request.ManagersSignupRequestDto;
import com.sparta.delivery_app.domain.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ThanksServiceSupportTest {

    private List<ConsumersSignupRequestDto> consumerRequestDtoList;
    private ManagersSignupRequestDto managerRequestDto;
    private RegisterStoreRequestDto storeRequestDto;
    private UserReviewAddRequestDto userReviewAddRequestDto;

    @BeforeEach
    public void setUp() {
        consumerRequestDtoList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ConsumersSignupRequestDto consumerRequestDto = new ConsumersSignupRequestDto(
                    "email" + i + "@g.com", "password" + i, "User" + i,
                    "Nickname" + i, "Address" + i);

            consumerRequestDtoList.add(consumerRequestDto);
        }

        managerRequestDto = new ManagersSignupRequestDto(
                "manager@g.com",
                "password123$",
                "manager",
                "managerNick1",
                "Store is here"
        );
        storeRequestDto = new RegisterStoreRequestDto(
                "EveryFood",
                "First Store",
                "123-45-67890",
                10000L,
                "SelfService!"
        );
        userReviewAddRequestDto = new UserReviewAddRequestDto(
                "맛집입니다.",
                "http://",
                5
        );

    }

    @Test
    @DisplayName("thanks 등록")
    public void addOrCancel_add() {
        //given
        User consumer1ForReviewer = User.saveUser(consumerRequestDtoList.get(0));
        User consumer2ForThanks = User.saveUser(consumerRequestDtoList.get(1));
        User Manager = User.saveUser(managerRequestDto);
        Store store = Store.of(storeRequestDto, Manager);
        Order orderByConsumer1 = Order.saveOrder(consumer1ForReviewer, store);
        UserReviews reviewByConsumer1 = UserReviews.saveReview(orderByConsumer1, consumer1ForReviewer, userReviewAddRequestDto);

        //when
        ThanksServiceSupport.addOrCancel(consumer2ForThanks, reviewByConsumer1.getId(), reviewByConsumer1);
        //then
        assertThat(consumer2ForThanks.getThanksList().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("thanks 취소")
    public void addOrCancel_cancel() {
        //given
        User consumer1ForReviewer = User.saveUser(consumerRequestDtoList.get(0));
        User consumer2ForThanks = User.saveUser(consumerRequestDtoList.get(1));
        User Manager = User.saveUser(managerRequestDto);
        Store store = Store.of(storeRequestDto, Manager);
        Order orderByConsumer1 = Order.saveOrder(consumer1ForReviewer, store);

        UserReviews userReviews = UserReviews.builder()
                .id(1L)
                .content(userReviewAddRequestDto.content())
                .rating(userReviewAddRequestDto.rating())
                .order(orderByConsumer1)
                .user(consumer1ForReviewer)
                .reviewStatus(ReviewStatus.ENABLE)
                .build();

        //when - then
        ThanksServiceSupport.addOrCancel(consumer2ForThanks, userReviews.getId(), userReviews);
        assertThat(consumer2ForThanks.getThanksList().size()).isEqualTo(1);

        ThanksServiceSupport.addOrCancel(consumer2ForThanks, userReviews.getId(), userReviews);
        assertThat(consumer2ForThanks.getThanksList().size()).isEqualTo(0);
    }

}