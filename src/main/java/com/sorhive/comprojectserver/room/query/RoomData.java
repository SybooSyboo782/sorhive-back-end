package com.sorhive.comprojectserver.room.query;

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
 * Class : RoomData
 * Comment: 방 데이터 전송객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-09       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Entity
@Table(name = "tbl_rooms")
@Getter
public class RoomData {

    @Id
    @Column(name="room_id")
    private Long id;

    @Column(name = "room_creator_code")
    private Long creatorCode;

    @Column(name = "room_creator_name")
    private String creatorName;

    @Column(name = "room_creator_id")
    private String creatorId;

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

    public void setRoomCount(Long roomCount) {
        this.roomCount = roomCount;
    }
}
