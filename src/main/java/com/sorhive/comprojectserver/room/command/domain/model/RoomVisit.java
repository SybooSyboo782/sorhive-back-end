package com.sorhive.comprojectserver.room.command.domain.model;

import com.sorhive.comprojectserver.common.entity.BaseTimeEntity;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import com.sorhive.comprojectserver.room.command.domain.model.room.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomVisit extends BaseTimeEntity {

    @Id
    @Column(name="room_visit_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public RoomVisit(Member memberCode, Room room) {
        this.member = memberCode;
        this.room = room;
    }
}
