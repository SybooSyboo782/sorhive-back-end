package com.sorhive.comprojectserver.room.command.application.dto;

import lombok.Getter;

/**
 * <pre>
 * Class : FurnitureImageCreateDto
 * Comment: 가구 이미지 생성 요청 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-17       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class FurnitureImageCreateRequestDto {

    private Long roomId;
    byte[] furnitureImage;
    Integer furnitureImageNo;
    String furnitureImageName;

}
