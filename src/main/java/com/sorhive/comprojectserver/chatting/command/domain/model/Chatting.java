package com.sorhive.comprojectserver.chatting.command.domain.model;

import lombok.Getter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * <pre>
 * Class : Chatting
 * Comment: 채팅 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-02       부시연           최초 생성
 * 2022-11-17       부시연           컬럼 일부 변경(회원 1, 회원 2로 구분)
 * 2022-11-21       부시연           마지막 채팅 건에 대한 응답 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Entity
@Table(name = "tbl_chatting")
@Getter
public class Chatting {

    @Id
    @Column(name="chatting_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chattingNo;

    @Column(name = "chatting_member_1")
    private Long memberCode1;

    @Column(name = "chatting_member_2")
    private Long memberCode2;

    @Column(name = "chatting_id")
    private String chattingId;

    @Column(name = "chatting_upload_time")
    private Timestamp uploadTime;

    protected Chatting() { }

    public Chatting(Long memberCode1, Long memberCode2, String chattingId) {

        this.memberCode1 = memberCode1;
        this.memberCode2 = memberCode2;
        this.chattingId = chattingId;
        this.uploadTime = new Timestamp(System.currentTimeMillis());

    }
}
