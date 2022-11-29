package com.sorhive.comprojectserver.room.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * <pre>
 * Class : GuestBookData
 * Comment: 방명록 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-13       부시연           최초 생성
 * 2022-11-22       부시연           변수명 수정
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Entity
@Table(name = "tbl_guestbooks")
@Getter
public class GuestBookData {

    @Id
    @Column(name="guestbook_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guestBookId;

    @Column(name = "guestbook_content")
    private String guestBookContent;

    @Column(name = "guestbook_writer_code")
    private Long guestBookWriterCode;

    @Column(name = "guestbook_writer_name")
    private String guestBookWriterName;

    @Column(name = "guestbook_writer_id")
    private String guestBookWriterId;

    @JsonIgnore
    @Column(name= "room_id")
    private Long roomId;

    @Column(name = "guestbook_create_time")
    @CreationTimestamp
    private Date guestBookCreateTime;

    @Column(name = "guestbook_upload_time")
    private Timestamp guestBookUploadTime;

    @Column(name = "guestbook_delete_yn")
    @ColumnDefault("'N'")
    private Character guestBookDeleteYn;

}
