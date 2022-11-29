package com.sorhive.comprojectserver.member.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Class : AvatarCreateDto
 * Comment: 아바타 생성 요청 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-07       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AvatarRequestDto {

    @NotNull
    private Integer faceType;

    @NotNull
    private Integer eyeBrowsType;

    @NotNull
    private Integer eyeType;

    @NotNull
    private Integer hairType;

    @NotNull
    private byte[] memberAvatarImage;

}