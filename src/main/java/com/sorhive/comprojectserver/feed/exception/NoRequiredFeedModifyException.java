package com.sorhive.comprojectserver.feed.exception;

/**
 * <pre>
 * Class : NoRequiredFeedModifyException
 * Comment: 피드 수정에 필요한 것이 없을 때 발생하는 예외처리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-20       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public class NoRequiredFeedModifyException extends RuntimeException {

    public NoRequiredFeedModifyException() {
        super();
    }

    public NoRequiredFeedModifyException(String message) {
        super(message);
    }

    public NoRequiredFeedModifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRequiredFeedModifyException(Throwable cause) {
        super(cause);
    }

}

