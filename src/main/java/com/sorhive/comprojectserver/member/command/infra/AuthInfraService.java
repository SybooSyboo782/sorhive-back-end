package com.sorhive.comprojectserver.member.command.infra;

import com.sorhive.comprojectserver.member.command.application.dto.EmailRequestDto;
import com.sorhive.comprojectserver.member.command.application.dto.FindIdRequestDto;
import com.sorhive.comprojectserver.member.command.application.dto.ResetPasswordRequestDto;
import com.sorhive.comprojectserver.member.command.domain.model.member.Member;
import com.sorhive.comprojectserver.member.command.domain.repository.MemberRepository;
import com.sorhive.comprojectserver.member.command.exception.AlreadyExistEmailException;
import com.sorhive.comprojectserver.member.command.exception.NoEmailException;
import com.sorhive.comprojectserver.member.command.exception.NoMemberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

/**
 * <pre>
 * Class : AuthInfraService
 * Comment: 인증 인프라 서비스
 * History
 * ================================================================
 * DATE             AUTHOR           NOTE
 * ----------------------------------------------------------------
 * 2022-11-17       부시연           최초 생성
 * 2022-11-17       부시연           이메일 인증
 * 2022-11-18       부시연           인증 코드 생성
 * 2022-11-20       부시연           회원 아이디 찾기 추가
 * 2022-11-21       부시연           비밀번호 재설정 추가
 * </pre>
 *
 * @author 부시연(최초 작성자)
 * @version 1(클래스 버전)
 * @see (참고할 class 또는 외부 url)
 */
@Service
public class AuthInfraService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private static final Logger log = LoggerFactory.getLogger(AuthInfraService.class);
    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;
    private final PasswordEncoder passwordEncoder;

    public AuthInfraService(MemberRepository memberRepository, JavaMailSender javaMailSender, SpringTemplateEngine springTemplateEngine, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.javaMailSender = javaMailSender;
        this.springTemplateEngine = springTemplateEngine;
        this.passwordEncoder = passwordEncoder;
    }


    /** 이메일 중복 검사 */
    public String emailAuthentication(EmailRequestDto emailRequestDto) throws MessagingException {

        log.info("[AuthInfraService] emailAuthentication Start ============================");
        log.info("[AuthInfraService] " + emailRequestDto);

        int countEmail = memberRepository.countByMemberEmail(emailRequestDto.getEmail());

        if(countEmail > 0) {
            log.warn("[AuthInfraService] AlreadyExistEmailException ");
            throw new AlreadyExistEmailException("해당 이메일은 이미 존재합니다.");
        }

        String certCode = emailSend(emailRequestDto.getEmail(), "SORHIVE 회원가입 인증 번호", "mail", certCode());

        log.info("[AuthInfraService] emailAuthentication End ============================");
        
        return certCode;

    }

    /** 아이디 찾기 */
    public String findId(FindIdRequestDto findIdRequestDto) throws MessagingException {

        log.info("[AuthInfraService] findId Start ============================");
        log.info("[findIdRequestDto] " + findIdRequestDto);

        String email = findIdRequestDto.getEmail();
        String name = findIdRequestDto.getName();

        int countEmail = memberRepository.countByMemberEmail(findIdRequestDto.getEmail());

        if(countEmail == 0) {
            log.warn("[AuthInfraService] NoEmailException ");
            throw new NoEmailException("해당 이메일은 존재 하지 않습니다.");
        }

        if(memberRepository.findByMemberEmailAndMemberNameAndDeleteYnEquals(email, name, 'N') == null) {
            log.warn("[AuthInfraService] NoEmailException ");
            throw new NoMemberException("해당 회원은 존재하지 않습니다.");
        }

        Member member = memberRepository.findByMemberEmailAndMemberNameAndDeleteYnEquals(email, name, 'N');

        return emailSend(email,"SORHIVE 아이디 찾기", "findid", member.getMemberId().getId());

    }

    /** 비밀번호 재설정 */
    public Object resetPassword(ResetPasswordRequestDto resetPasswordRequestDto) throws MessagingException {

        log.info("[AuthInfraService] resetPassword Start ============================");
        log.info("[resetPasswordRequestDto] " + resetPasswordRequestDto);

        String email = resetPasswordRequestDto.getEmail();
        String name = resetPasswordRequestDto.getName();

        int countEmail = memberRepository.countByMemberEmail(resetPasswordRequestDto.getEmail());

        if(countEmail == 0) {
            log.warn("[AuthInfraService] NoEmailException ");
            throw new NoEmailException("해당 이메일은 존재 하지 않습니다.");
        }

        if(memberRepository.findByMemberEmailAndMemberNameAndDeleteYnEquals(email, name, 'N') == null) {
            log.warn("[AuthInfraService] NoEmailException ");
            throw new NoMemberException("해당 회원은 존재하지 않습니다.");
        }

        Member member = memberRepository.findByMemberEmailAndMemberNameAndDeleteYnEquals(email, name, 'N');

        String code = certCode();

        String tempPassword = passwordEncoder.encode(code);

        member.changePassword(tempPassword);
        memberRepository.save(member);

        return emailSend(email,"SORHIVE 비밀번호 재설정", "resetpassword", code);
    }

    /** 인증번호 생성 */
    public String certCode() {

        log.info("[AuthInfraService] certCode Start ============================");

        Random random = new Random();
        int createNumber = 0;
        String tempNumber = "";
        String certCode = "";

        for (int i = 0; i < 6; i++) {

            createNumber = random.nextInt(9);
            tempNumber = Integer.toString(createNumber);
            certCode += tempNumber;

        }

        log.info("[AuthInfraService] certCode End ============================");

        return certCode;
    }

    /** 이메일 보내기 */
    public String emailSend(String toEmail, String title, String template, String thymeleafText) throws MessagingException {

        log.info("[AuthInfraService] emailSend Start ============================");

        String setFrom = fromEmail;

        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(setFrom); //보내는 이메일
        message.setText(setContext(template, thymeleafText), "utf-8", "html");

        javaMailSender.send(message);

        log.info("[AuthInfraService] emailSend End ============================");

        return thymeleafText;

    }

    /** 타임리프를 이용한 context 설정 */
    public String setContext(String template, String thymeleafText) {

        log.info("[AuthInfraService] setContext Start ============================");

        Context context = new Context();
        context.setVariable("thymeleafText", thymeleafText);

        log.info("[AuthInfraService] setContext End ============================");

        return springTemplateEngine.process(template, context);
    }

}
