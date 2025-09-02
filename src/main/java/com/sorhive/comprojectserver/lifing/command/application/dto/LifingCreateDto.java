package com.sorhive.comprojectserver.lifing.command.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * <pre>
 * Class : LifingCreateDto
 * Comment: 라이핑 작성 요청 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-12       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Getter
public class LifingCreateDto {

    @NotNull(message = "라이핑 아이디가 없습니다.")
    private Long lifingId;

    @NotNull(message = "라이핑 번호가 없습니다.")
    private Long lifingNo;

    @NotNull(message = "라이핑 카테고리 번호가 없습니다.")
    private Long lifingCategoryNo;

    @NotNull(message = "라이핑 내용이 없습니다.")
    private String lifingContent;

}
