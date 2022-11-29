package com.sorhive.comprojectserver.lifing.command.application.dto;

import lombok.Getter;

import java.sql.Timestamp;

/**
 * <pre>
 * Class : ResponseHoneyDeleteDto
 * Comment: 허니 제거 응답 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-12       부시연           최초 생성
 * 2022-11-15       부시연           라이핑으로 이동
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class LifingHoneyDeleteResponseDto {

    private Long lifingHoneyId;
    private Long memberCode;
    private Long lifingId;
    private Timestamp deleteTime;

    protected LifingHoneyDeleteResponseDto() {}

    public LifingHoneyDeleteResponseDto(Long id, Long lifingId, Long memberCode, Timestamp deleteTime) {

        this.lifingHoneyId = id;
        this.memberCode = memberCode;
        this.lifingId = lifingId;
        this.deleteTime = deleteTime;
    }
}
