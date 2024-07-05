package com.sparta.delivery_app.domain.review.repository;

import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserReviewsRepository extends JpaRepository<UserReviews, Long>, UserReviewsQueryRepository {

    Page<UserReviews> findAllByReviewStatus(Pageable pageable, ReviewStatus reviewStatus);

}
