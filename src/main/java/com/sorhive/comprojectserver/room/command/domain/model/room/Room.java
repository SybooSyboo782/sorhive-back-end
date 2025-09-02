package com.sorhive.comprojectserver.room.command.domain.model.room;

import com.sorhive.comprojectserver.common.entity.SoftDeleteEntity;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import com.sorhive.comprojectserver.room.command.domain.model.FurnitureImage;
import com.sorhive.comprojectserver.room.command.domain.model.GuestBook;
import com.sorhive.comprojectserver.room.command.domain.model.RoomVisit;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Class : Room
 * Comment: 방 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-08       부시연           최초 생성
 * 2022-11-09       부시연           몽고 DB 도메인으로 변경
 * 2022-11-15       부시연           방 조회수 문제 해결
 * 2022-11-17       부시연           가구 이미지 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Getter
@Entity
@Table(name = "tbl_rooms")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends SoftDeleteEntity {

    @Id
    @Column(name="room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_creator")
    private Member roomCreator;

    @Column(name="room_no")
    private String roomNo;

    @Column(name = "room_count")
    @ColumnDefault("0")
    private Long roomCount;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<GuestBook> guestBooks = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomVisit> roomVisits = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<FurnitureImage> furnitureImages = new ArrayList<>();

    public Room(Long id, String roomNo, Member roomCreator) {
        this.id = id;
        this.roomNo = roomNo;
        this.roomCreator = roomCreator;
    }

    /** 방 조회수 세기 */
    public void addRoomCount(Long roomCount) {
        /* 만약 방 조회수가 null일 경우면 0으로 초기화 한다. */
        if (this.roomCount == null) {
            this.roomCount = 0L;
        }

        this.roomCount += roomCount;
    }

}