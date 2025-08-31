package com.sorhive.comprojectserver.room.command.domain.roomvisit;

import com.sorhive.comprojectserver.member.command.domain.model.member.MemberCode;
import com.sorhive.comprojectserver.room.command.domain.room.Room;
import com.sorhive.comprojectserver.room.command.domain.room.RoomId;

import jakarta.persistence.*;
import java.sql.Timestamp;

/**
 * <pre>
 * Class : RoomVisit
 * Comment: 방 조회수 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-02       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Entity
@Table(name = "tbl_room_visits")
public class RoomVisit {

    @Id
    @Column(name="room_visit_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_visit_time")
    private Timestamp time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_room_id")
    private Room room;

    @Embedded
    private MemberCode memberCode;

    @Embedded
    private RoomId roomId;

    protected RoomVisit() {}

    public RoomVisit(MemberCode memberCode, RoomId roomId) {
        this.memberCode = memberCode;
        this.roomId = roomId;
        this.time = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public Timestamp getTime() {
        return time;
    }
}
