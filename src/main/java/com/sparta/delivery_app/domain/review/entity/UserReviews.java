package com.sparta.delivery_app.domain.review.entity;

import com.sparta.delivery_app.domain.commen.BaseTimeEntity;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.review.dto.request.UserReviewAddRequestDto;
import com.sparta.delivery_app.domain.review.dto.request.UserReviewModifyRequestDto;
import com.sparta.delivery_app.domain.thanks.entity.Thanks;
import com.sparta.delivery_app.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_reviews")
public class UserReviews extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_reviews_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(length = 400)
    private String reviewImagePath;

    @Column(nullable = false)
    private int rating;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column
    @OneToOne(mappedBy = "userReviews", fetch = FetchType.LAZY)
    private ManagerReviews managerReviews;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    private List<Thanks> thanksList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus reviewStatus;

    @Builder
    public UserReviews(String content, String reviewImagePath, int rating, Order order, User user, ReviewStatus reviewStatus) {
        this.content = content;
        this.reviewImagePath = reviewImagePath;
        this.rating = rating;
        this.order = order;
        this.user = user;
        this.reviewStatus = reviewStatus;
    }

    public static UserReviews saveReview(Order order, User user, UserReviewAddRequestDto requestDto) {
        return UserReviews.builder()
                .content(requestDto.content())
                .rating(requestDto.rating())
                .order(order)
                .user(user)
                .reviewStatus(ReviewStatus.ENABLE)
                .build();
    }

    public void updateReviewImagePath(String reviewImagePath) {
        this.reviewImagePath = reviewImagePath;
    }

    public UserReviews updateReview(UserReviewModifyRequestDto requestDto) {
        this.content = requestDto.content();
        this.rating = requestDto.rating();
        return this;
    }

    public void deleteReview() {
        this.reviewStatus = ReviewStatus.DISABLE;
    }

    // 추가 구현 필요 내용
    // 리뷰가 삭제될 경우 도움이돼요로 등록한 리스트 내역 모두 삭제
}
