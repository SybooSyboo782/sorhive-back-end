package com.sorhive.comprojectserver.member.command.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <pre>
 * Class : ExistFollowException
 * Comment: 이미 팔로우를 하는 상황에서 다시 팔로우를 할 때 발생하는 예외처리
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
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "이미 팔로우를 하고 있습니다")
public class ExistFollowException extends RuntimeException {
    public ExistFollowException() {
        super();
    }

    public ExistFollowException(String message) {
        super(message);
    }

    public ExistFollowException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistFollowException(Throwable cause) {
        super(cause);
    }

}
