package com.sorhive.comprojectserver.member.query.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sorhive.comprojectserver.member.command.domain.model.member.MemberRole;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * <pre>
 * Class : MemberData
 * Comment: 멤버 조회를 위한 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-10-31       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Entity
@Table(name = "tbl_members")
@Getter
public class MemberData {

    @Id
    @Column(name = "member_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberCode;

    @Column(name = "member_id")
    private String id;

    @Column(name = "member_name")
    private String name;

    @Column(name = "member_delete_yn")
    @ColumnDefault("N")
    private Character deleteYn;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ROLE_MEMBER'")
    @JsonIgnore
    private MemberRole memberRole;

    @Column(name = "member_room_image")
    @ColumnDefault("''")
    private String memberRoomImage;

    @Column(name = "member_avatar_image")
    @ColumnDefault("''")
    private String avatarImagePath;

    @Column(name = "member_lifing_no")
    @ColumnDefault("-1")
    private Long lifingNo;

    @Column(name = "member_lifing_category_no")
    @ColumnDefault("-1")
    private Long lifingCategoryNo;

    @Column(name = "member_lifing_yn")
    @ColumnDefault("'N'")
    private Character lifingYn;

    @Column(name = "member_avatar_yn")
    @ColumnDefault("'N'")
    private Character avatarYn;

    protected MemberData() {
    }

}
