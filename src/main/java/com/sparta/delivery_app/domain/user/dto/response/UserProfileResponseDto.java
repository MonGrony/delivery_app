package com.sparta.delivery_app.domain.user.dto.response;

import com.sparta.delivery_app.domain.user.dto.SimpleProfileElements;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponseDto {

    private String nickname;
    private Long likeCount;
    private Long reviewCount;
    private Long thanksCount;

    public static UserProfileResponseDto of(SimpleProfileElements elements) {
        return UserProfileResponseDto.builder()
                .nickname(elements.getNickname())
                .likeCount(elements.getLikeCount())
                .reviewCount(elements.getReviewCount())
                .thanksCount(elements.getThanksCount())
                .build();
    }
}
