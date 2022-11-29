package com.sorhive.comprojectserver.feed.exception;
/**
 * <pre>
 * Class : NoFeedImageException
 * Comment: 해당 피드 이미지가 없을 때 발생하는 예외처리
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-12       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
public class NoFeedImageException extends RuntimeException {
    public NoFeedImageException() {
        super();
    }

    public NoFeedImageException(String message) {
        super(message);
    }

    public NoFeedImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFeedImageException(Throwable cause) {
        super(cause);
    }

}