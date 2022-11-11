package com.sorhive.comprojectserver.member.query.member;

import com.sorhive.comprojectserver.member.command.domain.model.member.MemberRole;
import com.sorhive.comprojectserver.member.command.domain.model.member.Password;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * <pre>
 * Class : MemberData
 * Comment: 클래스에 대한 간단 설명
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

    @Column(name = "member_room_image")
    private String memberRoomImage;

    protected MemberData() {
    }


}
