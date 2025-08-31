package com.sorhive.comprojectserver.room.command.domain.guestbook;

import com.sorhive.comprojectserver.room.command.domain.room.Room;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

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
public class GuestBook {

    @Id
    @Column(name="guestbook_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guestbook_content")
    private String content;

    @Embedded
    private GuestBookWriter guestBookWriter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    /* 기본 생성 시간 추가 */
    @Column(name = "guestbook_create_time")
    @CreationTimestamp
    private Date createTime;

    @Column(name = "guestbook_upload_time")
    private Timestamp uploadTime;

    @Column(name = "guestbook_delete_time")
    private Timestamp deleteTime;

    @Column(name = "guestbook_delete_yn")
    @ColumnDefault("'N'")
    private Character deleteYn;

    protected GuestBook () {}

    public GuestBook(String guestBookContent, GuestBookWriter guestBookWriter, Room room) {

        this.content = guestBookContent;
        this.room = room;
        this.guestBookWriter = guestBookWriter;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.uploadTime = new Timestamp(System.currentTimeMillis());
        this.deleteYn = 'N';

    }

    public void updateGuestBook(String guestBookContent) {

        this.content = guestBookContent;
        this.uploadTime = new Timestamp(System.currentTimeMillis());

    }

    public void deleteGuestBook(char deleteYn) {
        this.deleteTime = new Timestamp(System.currentTimeMillis());
        this.deleteYn = deleteYn;
    }
}