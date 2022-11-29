package com.sorhive.comprojectserver.lifing.query;

import com.sorhive.comprojectserver.lifing.query.dto.LifingCommentData;
import com.sorhive.comprojectserver.lifing.query.dto.LifingData;
import com.sorhive.comprojectserver.lifing.query.dto.LifingImagePath;
import com.sorhive.comprojectserver.lifing.query.dto.LifingSummary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * Class : LifingImageMapper
 * Comment: 라이핑 이미지 매퍼
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-12       부시연           최초 생성
 * 2022-11-12       부시연           라이핑 이미지 회원 번호로 최신 이미지 찾기 추가
 * 2022-11-14       부시연           라이핑 전체 조회 추가
 * 2022-11-15       부시연           라이핑 댓글 목록 조회 추가
 * 2022-11-16       부시연           라이핑 이미지 목록 조회 추가
 * 2022-11-16       부시연           라이핑 이미지 회원 번호로 최신 이미지 찾기 제거
 * 2022-11-23       부시연           회원 상세 조회 기능 변경
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Mapper
public interface LifingMapper {

    List<LifingData> findAllLifingByMemberCode(int pageNo, int memberCode);

    List<LifingCommentData> selectAllLifingComments(Long lifingId);

    LifingImagePath selectLifingImage(Long lifingId);

    LifingSummary findOneByCreateTime(Long writerCode);
}
