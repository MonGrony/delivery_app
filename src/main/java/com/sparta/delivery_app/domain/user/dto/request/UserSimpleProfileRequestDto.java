package com.sparta.delivery_app.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserSimpleProfileRequestDto {

    @NotBlank
    private String email;
}
