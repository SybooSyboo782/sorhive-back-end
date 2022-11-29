package com.sorhive.comprojectserver.member.command.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <pre>
 * Class : NoMemberException
 * Comment: 없는 회원 예외처리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-10-31       부시연           최초 생성
 * 2022-11-24       부시연           HttpStatus 상태값 반환 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "해당 회원은 존재 하지 않습니다")
public class NoMemberException extends RuntimeException {
    public NoMemberException() {
        super();
    }

    public NoMemberException(String message) {
        super(message);
    }

    public NoMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoMemberException(Throwable cause) {
        super(cause);
    }

}
