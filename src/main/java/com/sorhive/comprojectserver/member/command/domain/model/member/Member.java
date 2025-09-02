package com.sorhive.comprojectserver.member.command.domain.model.member;

import com.sorhive.comprojectserver.common.entity.SoftDeleteEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;

/**
 * <pre>
 * Class : Member
 * Comment: 회원 도메인 모델
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-04       부시연           최초 생성
 * 2022-11-06       부시연           임베디드 멤버코드 롱으로 변경
 * 2022-11-10       부시연           방이미지 추가
 * 2022-11-10       부시연           아바타 이미지 추가
 * 2022-11-12       부시연           라이핑 번호 추가
 * 2022-11-15       부시연           라이핑 번호 로직 수정
 * 2022-11-20       부시연           비밀번호 재설정 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
@Entity
@Table(name = "tbl_members")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends SoftDeleteEntity implements UserDetails {

    @Id
    @Column(name = "member_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberCode;

    @Column(name = "member_id", unique = true)
    private String memberId;

    @Column(name = "member_name")
    private String memberName;

    @Column(name ="member_email")
    private String memberEmail;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ROLE_MEMBER'")
    private MemberRole memberRole;

    @Column(name = "password")
    private String password;

    @Column(name = "member_room_image")
    @ColumnDefault("''")
    private String roomImagePath;

    @Column(name = "member_avatar_image")
    @ColumnDefault("''")
    private String avatarImagePath;

    @Column(name = "member_avatar_yn")
    @ColumnDefault("'N'")
    private Character avatarYn;

    @Column(name = "member_lifing_no")
    @ColumnDefault("-1")
    private Long lifingNo;

    @Column(name = "member_lifing_category_no")
    @ColumnDefault("-1")
    private Long lifingCategoryNo;

    @Column(name = "member_lifing_yn")
    @ColumnDefault("'N'")
    private Character lifingYn;

    public Member(String memberId, String memberName, String password) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.password = password;
    }
    @Builder
    public Member(Long memberCode, String memberId, String memberName, String memberEmail, MemberRole memberRole, String password, Timestamp createTime, Timestamp uploadTime, Collection<? extends GrantedAuthority> authorities) {
        this.memberCode = memberCode;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberRole = memberRole;
        this.password = password;
        this.authorities = authorities;
    }

    public Long getMemberCode() {
        return memberCode;
    }

    public String getMemberId() { return memberId; }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public MemberRole getMemberRole() { return memberRole; }

    public String getRoomImagePath() { return roomImagePath; }

    public String getAvatarImagePath() { return avatarImagePath; }

    public Long getLifingCategoryNo() { return lifingCategoryNo; }

    public Long getLifingNo() { return lifingNo; }

    public Character getLifingYn() { return lifingYn; }

    public Character getAvatarYn() { return avatarYn; }

    public void setRoomImagePath(String roomImagePath) {
        this.roomImagePath = roomImagePath;
    }

    public void createAvatarImagePath(String avatarImagePath) {
        this.avatarImagePath = avatarImagePath;
        this.roomImagePath = "https://combucket.s3.ap-northeast-2.amazonaws.com/rooms/default_room.png";
        this.avatarYn = 'Y';
    }

    public void setAvatarImagePath(String avatarImagePath) {
        this.avatarImagePath = avatarImagePath;
    }

    /** AI가 분석한 사진이 존재 하는 라이핑 생성 */
    public void changeLifingWithAI(Long lifingNo, Long lifingCategoryNo) {
        this.lifingNo = lifingNo;
        this.lifingCategoryNo = lifingCategoryNo;
        this.lifingYn = 'Y';
    }

    /** 그냥 사진만 올리는 라이핑 생성 */
    public void changeLifingWithoutAI() {
        this.lifingNo = -1L;
        this.lifingCategoryNo = -1L;
        this.lifingYn = 'Y';
    }

    // 이하 코드는 security 를 위한 용도
    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.memberId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void changePassword(String tempPassword) {
        this.password = tempPassword;
    }

    public void updateMember(String name) {
        this.memberName = name;
    }
}
