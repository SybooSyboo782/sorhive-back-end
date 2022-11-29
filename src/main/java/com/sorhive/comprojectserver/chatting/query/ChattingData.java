package com.sorhive.comprojectserver.chatting.query;

import lombok.Getter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * <pre>
 * Class : ChattingData
 * Comment: 채팅 데이터 조회 엔티티
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-18       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Entity
@Table(name = "tbl_chatting")
@Getter
public class ChattingData {

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

    protected ChattingData() { }

}
