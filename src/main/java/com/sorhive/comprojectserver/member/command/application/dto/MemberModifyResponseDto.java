package com.sorhive.comprojectserver.member.command.application.dto;

import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <pre>
 * Class : MemberModifyResponseDto
 * Comment: 회원 수정 응답 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-21       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class MemberModifyResponseDto {

    private Long memberCode;
    private String memberId;
    private String memberName;
    private Timestamp uploadTime;

    protected MemberModifyResponseDto () {}

    public MemberModifyResponseDto(Long memberCode, String memberId, String memberName, LocalDateTime uploadTime) {
        this.memberCode = memberCode;
        this.memberId = memberId;
        this.memberName = memberName;
        this.uploadTime = Timestamp.valueOf(uploadTime);
    }
}
