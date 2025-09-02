package com.sorhive.comprojectserver.room.command.domain.model.room;

import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member roomCreator;

    private List<Map<String,Object>> furnitures;

    public MongoRoom(Member roomCreator, List<Map<String, Object>> furnitures) {
        setRoomCreator(roomCreator);
        setFurnitures(furnitures);
    }

    private void setFurnitures(List<Map<String, Object>> furnitures) {
        this.furnitures = furnitures;
    }

    private void setRoomCreator(Member roomCreator) {
        this.roomCreator = roomCreator;
    }
}
