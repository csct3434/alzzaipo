package csct3434.ipo.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import csct3434.ipo.config.SessionConfig;
import csct3434.ipo.service.KakaoLoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/login/kakao")
    public String requestAuthorizationCode() {
        String authCodeRequestUrl = kakaoLoginService.getAuthCodeRequestUrl();
        return "redirect:" + authCodeRequestUrl;
    }

    @GetMapping("/kakao_callback")
    public String kakaoLogin(@RequestParam("code") String code, HttpSession session) throws JsonProcessingException {
        kakaoLoginService.kakaoLogin(code, session);

        String sessionAccessToken = (String)session.getAttribute(SessionConfig.accessToken);
        Long sessionMemberId = (Long) session.getAttribute(SessionConfig.memberId);
        log.info("session established - memberId:" + sessionMemberId + " / accessToken:" + sessionAccessToken);

        return "index";
    }
}
