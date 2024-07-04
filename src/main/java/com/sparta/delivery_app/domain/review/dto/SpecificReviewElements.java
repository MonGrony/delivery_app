package com.sparta.delivery_app.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpecificReviewElements {

    private String content;
    private String reviewImagePath;
    private int rating;
    private Long storeId;
    private String userNickname;
    private Long thanksCount;
}
