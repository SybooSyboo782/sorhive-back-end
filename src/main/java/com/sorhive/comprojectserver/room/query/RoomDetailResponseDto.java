package com.sorhive.comprojectserver.room.query;

import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Class : RoomDetailResponseDto
 * Comment: 방 상세 조회 응답 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-13       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
public class RoomDetailResponseDto {

    private Long roomId;
    private String mongoRoomid;
    private Long roomCreator;
    private String roomCreatorName;
    private String roomCreatorId;
    private List<GuestBookData> guestBookDataList;
    private List<FurnitureImageData> furnitureImageDataList;
    private List<Map<String,Object>> furnitures;

    public RoomDetailResponseDto(Long roomId, Long roomCreator, String roomCreatorName, String roomCreatorId,
                                 List<Map<String, Object>> furnitures, String mongoRoomid) {
        this.roomId = roomId;
        this.mongoRoomid = mongoRoomid;
        this.roomCreator = roomCreator;
        this.roomCreatorName = roomCreatorName;
        this.roomCreatorId = roomCreatorId;
        this.furnitures = furnitures;
    }

    public void setGuestBookDataList(List<GuestBookData> guestBookDataList) {
        this.guestBookDataList = guestBookDataList;
    }

    public void setFurnitureImageDataList(List<FurnitureImageData> furnitureImageDataList) {
        this.furnitureImageDataList = furnitureImageDataList;
    }
}
