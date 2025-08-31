package com.sorhive.comprojectserver.room.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Class : RoomCreateDto
 * Comment: 방 생성 요청 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-07       부시연           최초 생성
 * 2022-11-10       부시연           접속용 방 이미지 대응
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RoomCreateDto {

    private List<Map<String,Object>> furnitures;

    @NotNull
    private byte[] roomImage;

}