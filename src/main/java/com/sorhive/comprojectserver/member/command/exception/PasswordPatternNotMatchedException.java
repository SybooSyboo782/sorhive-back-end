package com.sorhive.comprojectserver.member.command.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <pre>
 * Class : PasswordPatternNotMatchedException
 * Comment: 비밀번호 패턴 불일치 예외처리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-24       부시연           최초 생성
 * 2022-11-24       부시연           HttpStatus 상태값 반환 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "잘못된 비밀번호 요청입니다.")
public class PasswordPatternNotMatchedException extends RuntimeException {
    public PasswordPatternNotMatchedException() { super(); }

    public PasswordPatternNotMatchedException(String message) {
        super(message);
    }

    public PasswordPatternNotMatchedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordPatternNotMatchedException(Throwable cause) {
        super(cause);
    }

}
