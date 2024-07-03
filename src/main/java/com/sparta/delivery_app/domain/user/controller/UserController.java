package com.sparta.delivery_app.domain.user.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.user.dto.request.UserSimpleProfileRequestDto;
import com.sparta.delivery_app.domain.user.dto.response.UserProfileResponseDto;
import com.sparta.delivery_app.domain.user.dto.request.*;
import com.sparta.delivery_app.domain.user.dto.response.ConsumersSignupResponseDto;
import com.sparta.delivery_app.domain.user.dto.response.ManagersSignupResponseDto;
import com.sparta.delivery_app.domain.user.dto.response.UserProfileModifyResponseDto;
import com.sparta.delivery_app.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * consumers 회원가입
     *
     * @param requestDto
     * @return
     */
    @PostMapping("/consumers")
    public ResponseEntity<RestApiResponse<ConsumersSignupResponseDto>> ConsumersSignupRequestDto(
            @Valid @RequestBody final ConsumersSignupRequestDto requestDto) {
        ConsumersSignupResponseDto responseDto = userService.consumersUserAdd(requestDto);
        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("회원가입에 성공했습니다.",
                        responseDto)
                );
    }

    /**
     * mangers 회원가입
     *
     * @param requestDto
     * @return
     */
    @PostMapping("/managers")
    public ResponseEntity<RestApiResponse<ManagersSignupResponseDto>> managersSignupRequestDto(
            @Valid @RequestBody final ManagersSignupRequestDto requestDto) {
        ManagersSignupResponseDto responseDto = userService.managersUserAdd(requestDto);

        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("회원가입에 성공했습니다. 사장님, 우리 함께 성장해요!",
                        responseDto)
                );
    }

    /**
     * 회원탈퇴
     *
     * @param user
     * @param userResignRequestDto
     * @return
     */
    @PreAuthorize("hasAnyRole('CONSUMER','MANAGER')")
    @DeleteMapping("/resign")
    public ResponseEntity<RestApiResponse<String>> resign(
            @AuthenticationPrincipal AuthenticationUser user,
            @Valid @RequestBody final UserResignRequestDto userResignRequestDto
    ) {
        userService.resignUser(user, userResignRequestDto);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("탈퇴 되었습니다."));
    }

    /**
     * 프로필 수정
     *
     * @param user
     * @param requestDto
     * @return
     */
    @PreAuthorize("hasAnyRole('CONSUMER','MANAGER')")
    @PutMapping("/profile")
    public ResponseEntity<RestApiResponse<UserProfileModifyResponseDto>> profileModify(
            @AuthenticationPrincipal AuthenticationUser user,
            @Valid @RequestBody final UserProfileModifyRequestDto requestDto
    ) {
        UserProfileModifyResponseDto responseDto = userService.modifyProfileUser(user, requestDto);
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of(responseDto));
    }

    /**
     * 비밀번호 수정
     *
     * @param user
     * @param requestDto
     * @return
     */
    @PreAuthorize("hasAnyRole('CONSUMER','MANAGER')")
    @PatchMapping("/password")
    public ResponseEntity<RestApiResponse<String>> passwordModify(
            @AuthenticationPrincipal AuthenticationUser user,
            @Valid @RequestBody final UserPasswordModifyRequestDto requestDto
    ) {
        userService.modifyPasswordUser(user, requestDto);
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("비밀번호가 수정되었습니다."));
    }

    /**
     * 프로필 조회
     * - 닉네임 & 좋아요한 store '수' & 작성한 댓글 '수' & 도움이 됐어요 '수'
     */
    @PreAuthorize("hasAnyRole('CONSUMER','MANAGER')")
    @GetMapping("/profile")
    public ResponseEntity<RestApiResponse<UserProfileResponseDto>> getSimpleProfile(
            @AuthenticationPrincipal AuthenticationUser user,
            @Valid @RequestBody final UserSimpleProfileRequestDto requestDto
    ) {
        UserProfileResponseDto responseDto = userService.userSimpleProfile(user, requestDto);
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of(responseDto));
    }

}