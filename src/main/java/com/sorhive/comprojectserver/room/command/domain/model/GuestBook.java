package com.sorhive.comprojectserver.room.command.domain.model;

import com.sorhive.comprojectserver.common.entity.SoftDeleteEntity;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import com.sorhive.comprojectserver.room.command.domain.model.room.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * Class : GuestBook
 * Comment: 방명록 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-02       부시연           최초 생성
 * 2022-11-13       부시연           @CreationTimestamp 를 이용하여 기본 생성 시간 추가
 * 2022-11-16       부시연           updateGuestBook 방명록 수정 추가
 * 2022-11-16       부시연           deleteGuestBook 방명록 삭제 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Entity
@Table(name = "tbl_guestbooks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestBook extends SoftDeleteEntity {

    @Id
    @Column(name="guestbook_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guestbook_content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guestbook_writer")
    private Member guestBookWriter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public GuestBook(String guestBookContent, Member guestBookWriter, Room room) {
        this.content = guestBookContent;
        this.guestBookWriter = guestBookWriter;
        this.room = room;
    }

    public void updateGuestBook(String guestBookContent) {
        this.content = guestBookContent;
    }
}