package curevengers.petit_cure.controller;

import curevengers.petit_cure.Dto.EmailDto;
import curevengers.petit_cure.Service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {
    private final EmailService emailService;

    // 인증코드 메일 발송
    @GetMapping("/send")
    public String mailSend(EmailDto emailDto) throws MessagingException{
        log.info("EmailController.mailSend()");
        emailService.sendEmail(emailDto.getMail());
        return "인증코드가 발송되었습니다.";
    }

    // 인증코드 인증
    @GetMapping("/verify")
    public String verify(EmailDto emailDto) {
        log.info("EmailController.verify()");
        boolean isVerify = emailService.verifyEmailCode(emailDto.getMail(), emailDto.getVerifyCode());
        return isVerify ? "인증이 완료되었습니다." : "인증 실패하셨습니다.";
    }

}
