package com.sorhive.comprojectserver.room.execption;
/**
 * <pre>
 * Class : NoFurnitureImageException
 * Comment: 가구 이미지가 없을 때 예외처리
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
public class NoFurnitureImageException extends RuntimeException {
    public NoFurnitureImageException() {
        super();
    }

    public NoFurnitureImageException(String message) {
        super(message);
    }

    public NoFurnitureImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFurnitureImageException(Throwable cause) {
        super(cause);
    }

}