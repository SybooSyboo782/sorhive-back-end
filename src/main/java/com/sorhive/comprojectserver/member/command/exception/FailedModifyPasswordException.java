package com.sorhive.comprojectserver.member.command.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <pre>
 * Class : FailedModifyPasswordException
 * Comment: 비밀번호 수정에 대한 예외처리
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
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "비밀번호가 일치하지 않습니다.")
public class FailedModifyPasswordException extends RuntimeException {
    public FailedModifyPasswordException() {
        super();
    }

    public FailedModifyPasswordException(String message) {
        super(message);
    }

    public FailedModifyPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedModifyPasswordException(Throwable cause) {
        super(cause);
    }

}
