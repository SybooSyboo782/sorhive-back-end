package com.sorhive.comprojectserver.chatting.command.domain.model;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <pre>
 * Class : MongoChatting
 * Comment: 몽고 DB 채팅 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-14       부시연           최초 생성
 * 2022-11-17       부시연           컬럼 일부 변경(회원 1, 회원 2로 구분)
 * 2022-11-21       부시연           컬럼 일부 변경(회원의 이름, 방이미지 추가)
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Getter
@Document(collection = "chatting")
public class MongoChatting {

    @Id
    private String id;
    private Long counter;
    private Long memberCode1;
    private String memberName1;
    private String memberRoomImage1;
    private Long memberCode2;
    private String memberName2;
    private String memberRoomImage2;
    private List<String> messages;
    private LocalDateTime uploadTime;

    protected MongoChatting() {}

    public MongoChatting(Long memberCode1, String memberName1, String memberRoomImage1, Long memberCode2, String memberName2, String memberRoomImage2,List<String> messages) {
        if(counter == null) {
            counter = 0L;
        }
        this.counter += 1;
        this.messages = messages;
        this.memberCode1 = memberCode1;
        this.memberName1 = memberName1;
        this.memberRoomImage1 = memberRoomImage1;
        this.memberCode2 = memberCode2;
        this.memberName2 = memberName2;
        this.memberRoomImage2 = memberRoomImage2;
        this.uploadTime = LocalDateTime.now();
    }

}
