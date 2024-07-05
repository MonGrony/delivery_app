package com.sparta.delivery_app.domain.thanks.entity;

import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "reviews_thanks")
public class Thanks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thanks_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userReview_id")
    UserReviews userReviews;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Thanks(UserReviews userReviews, User user) {
        this.userReviews = userReviews;
        this.user = user;
    }

    public static void cancelThanks(User loginUser, Thanks thanks) {
        if (loginUser != null && loginUser.getThanksList() != null) {
            loginUser.getThanksList().remove(thanks);
        }
    }

}
