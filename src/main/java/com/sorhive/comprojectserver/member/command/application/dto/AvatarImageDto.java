package com.sorhive.comprojectserver.member.command.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * Class : AvatarImageDto
 * Comment: 아바타 이미지 생성 요청 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-07       부시연           최초 생성
 * 2022-11-11       부시연           멀티파트파일 -> 바이트배열로 변경
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@AllArgsConstructor
@Getter
public class AvatarImageDto {

    @NotNull
    private byte[] avatarImage;

    @NotNull
    private String avatarImageName;

}
