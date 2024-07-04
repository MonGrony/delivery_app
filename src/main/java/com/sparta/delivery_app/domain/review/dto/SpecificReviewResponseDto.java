package com.sparta.delivery_app.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpecificReviewResponseDto {

    private String content;
    private String reviewImagePath;
    private int rating;
    private Long storeId;
    private String userNickname;
    private Long thanksCount;

    public static SpecificReviewResponseDto of(SpecificReviewElements elements) {
        return SpecificReviewResponseDto.builder()
                .content(elements.getContent())
                .reviewImagePath(elements.getReviewImagePath())
                .rating(elements.getRating())
                .storeId(elements.getStoreId())
                .userNickname(elements.getUserNickname())
                .thanksCount(elements.getThanksCount())
                .build();
    }

}
