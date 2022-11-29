package com.sorhive.comprojectserver.config.dto;

/**
 * <pre>
 * Class : TokenDto
 * Comment: 토큰 전송 객체
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-06       부시연           최초 생성
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 */
public class TokenDto {
    private String grantType;

    private String memberName;
    private Long memberCode;
    private Character avatarYn;
    private String accessToken;
    private Long accessTokenExpiresIn;

    public TokenDto() {
    }

    public TokenDto(String grantType, String memberName, Long memberCode, Character avatarYn,String accessToken, Long accessTokenExpiresIn) {
        this.grantType = grantType;
        this.memberName = memberName;
        this.memberCode = memberCode;
        this.avatarYn = avatarYn;
        this.accessToken = accessToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getAccessTokenExpiresIn() {
        return accessTokenExpiresIn;
    }

    public void setAccessTokenExpiresIn(Long accessTokenExpiresIn) {
        this.accessTokenExpiresIn = accessTokenExpiresIn;
    }

    public Long getMemberCode() { return memberCode; }

    public void setMemberCode(Long memberCode) { this.memberCode = memberCode; }

    public Character getAvatarYn() { return avatarYn; }

    public void setAvatarYn(Character avatarYn) { this.avatarYn = avatarYn; }

    @Override
    public String toString() {
        return "TokenDto{" +
                "grantType='" + grantType + '\'' +
                ", memberName='" + memberName + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", accessTokenExpiresIn=" + accessTokenExpiresIn +
                '}';
    }
}