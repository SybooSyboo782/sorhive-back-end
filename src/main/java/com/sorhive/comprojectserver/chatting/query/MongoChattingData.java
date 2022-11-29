package com.sorhive.comprojectserver.chatting.query;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <pre>
 * Class : MongoChattingData
 * Comment: 클래스에 대한 간단 설명
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
@Getter
@Document(collection = "chatting")
public class MongoChattingData {

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

    protected MongoChattingData () {}
}
