package com.sparta.delivery_app.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleProfileElements {

    private String nickname;
    private Long likeCount;
    private Long reviewCount;
    private Long thanksCount;
}
