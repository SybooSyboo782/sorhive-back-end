package com.sorhive.comprojectserver.room.command.domain.room;

import com.sorhive.comprojectserver.room.command.domain.furnitureimage.FurnitureImage;
import com.sorhive.comprojectserver.room.command.domain.guestbook.GuestBook;
import com.sorhive.comprojectserver.room.command.domain.roomvisit.RoomVisit;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
public class Room {

    @Id
    @Column(name="room_id")
    private Long id;

    private RoomCreator roomCreator;

    @Column(name="room_no")
    private String roomNo;

    @Column(name = "room_count")
    @ColumnDefault("0")
    private Long roomCount;

    @Column(name = "room_create_time")
    @CreationTimestamp
    private Date createTime;

    @Column(name = "room_upload_time")
    private Timestamp uploadTime;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<GuestBook> guestBooks = new ArrayList<GuestBook>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomVisit> roomVisits = new ArrayList<RoomVisit>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<FurnitureImage> furnitureImages = new ArrayList<>();

    protected Room() { }

    public Room(Long id, String roomNo, RoomCreator roomCreator) {
        this.id = id;
        this.roomNo = roomNo;
        this.roomCreator = roomCreator;
        this.uploadTime = new Timestamp(System.currentTimeMillis());
    }

    /** 방 조회수 세기 */
    public void setRoomCount(Long roomCount) {

        /* 만약 방 조회수가 null일 경우면 0으로 초기화 한다. */
        if(this.roomCount == null) {
            this.roomCount = 0L;
        }

        this.roomCount += roomCount;
    }

}