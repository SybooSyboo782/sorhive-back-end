package com.sorhive.comprojectserver.room.command.domain.room;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Class : MongoRoom
 * Comment: 몽고 DB 방 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-08       부시연           최초 생성
 * 2022-11-09       부시연           몽고 DB 도메인으로 변경
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Getter
@Document(collection = "room")
public class MongoRoom {
    
    @Id
    private String id;
    private RoomCreator roomCreator;
    private List<Map<String,Object>> furnitures;

    public MongoRoom(RoomCreator roomCreator, List<Map<String, Object>> furnitures) {
        setRoomCreator(roomCreator);
        setFurnitures(furnitures);
    }

    public void setFurnitures(List<Map<String, Object>> furnitures) {
        this.furnitures = furnitures;
    }

    public void setRoomCreator(RoomCreator roomCreator) {
        this.roomCreator = roomCreator;
    }
}
