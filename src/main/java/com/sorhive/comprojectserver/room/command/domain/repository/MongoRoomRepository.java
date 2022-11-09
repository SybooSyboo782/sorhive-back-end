package com.sorhive.comprojectserver.room.command.domain.repository;

import com.sorhive.comprojectserver.room.command.domain.room.MongoRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <pre>
 * Class : MongoRoomRepository
 * Comment: 클래스에 대한 간단 설명
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-09       부시연           몽고DB Repository 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
public interface MongoRoomRepository extends MongoRepository<MongoRoom, String> {

}
