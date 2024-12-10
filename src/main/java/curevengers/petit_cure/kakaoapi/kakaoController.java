package curevengers.petit_cure.kakaoapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import curevengers.petit_cure.Dto.memberDTO;
import curevengers.petit_cure.Service.UserServiceImpl;
import curevengers.petit_cure.Service.userService;
import curevengers.petit_cure.controller.memberController;
import curevengers.petit_cure.security.userdetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.management.BadAttributeValueExpException;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class kakaoController {

    // kakao class 작성
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Autowired
    UserServiceImpl userService;
    @Autowired
    userdetailsService userdetailsservice;

    @GetMapping("/oauth2/code/kakao")
    public ModelAndView kakaoCallback(@RequestParam("code") String code, HttpServletRequest request) {
        System.out.println("콜백자리");
        ModelAndView mav = new ModelAndView();

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://kauth.kakao.com/oauth/token";
        // HTTP 요청을 간단하게 수행해주기 위해서 RestTemplate객체를 생성하고
        // 액세스 토큰을 요청할 때 해당 url로 HTTP POST요청을 보내야한다.
        MultiValueMap<String,String> parameters = new LinkedMultiValueMap<>();
        // 여러개의 값을 저장할 수 있는 맵을 선언하고 파라미터를 설정하여 요청한다.
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id",clientId);
        parameters.add("redirect_uri",redirectUri);
        parameters.add("code",code);

        HttpHeaders headers = new HttpHeaders();
        // HTTP요청 또는 응답의 헤더 정보를 담기 위한 클래스이다.
        // 새로운 HttpHeaders객체를 생성하여 카카오 API에 요청을 보낼 때 필요안 요청 헤더를 설정할 준비를함
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // headers에 HTTP Content-type헤더를 설정한다.
        // 데이터가 url인코딩 된 폼 형식으로 전송됨을 의미함
        HttpEntity<MultiValueMap<String,String>> requestEntity = new HttpEntity<>(parameters, headers);
        // HTTP요청의 본문과 헤더를 포함하는 객체로, 필요정보를 다 묶어서 보낸다.(parameters+headers)

        // 이제는 액세스 토큰을 요청하고, 이 토큰으로 사용자정보를 요청할 것
        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);
        String accessToken  = response.getBody().get("access_token").toString();
        // 응답부분에서 access_token에 해당하는 것만 추출
        
        HttpHeaders headers1= new HttpHeaders();
        headers1.set("Authorization","Bearer "+accessToken);
        HttpEntity<Void> request1 = new HttpEntity<>(headers1);
        // 토큰을 이용하여 응답코드를 완성하고, 카카오에 요청하여 사용자 정보를 가져옴
        ResponseEntity<Map> response1 = restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET, request1, Map.class);
        Map<String,Object> userInfo = response1.getBody();

        String kakaoId = userInfo.get("id").toString();
//        Map<String, String> properties =(Map<String, String>) userInfo.get("properties");
        Map<String,Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");
        String name = kakaoAccount.get("name").toString();
        String email = kakaoAccount.get("email").toString();
        String phone = kakaoAccount.get("phone_number").toString();
        String age = kakaoAccount.get("age_range").toString();
        String gender = kakaoAccount.get("gender").toString();
        // 우리는 이름, 이메일, 전화번호, 연령대, 성별이 필요함

        // 해당 정보들 중 전화번호로 회원을 조회할 것이다.
        String id = userService.getMemberByphone(phone);
        memberDTO member = userService.getMemberById(id);

        if(member != null) {
            // 이미 존재하는 회원이면 로그인 처리로 넘길 것이다.
            UserDetails userDetails = userdetailsservice.loadUserByUsername(member.getId());
            // 카카오로 실명 인증을 통하여 회원가입을 한 회원은 MEMBER권한을 자동 부여할 것이다.
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("MEMBER"));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
            System.out.println(userDetails.getAuthorities() +"---- 콜백자리에서 로그인 한 회원의 권한을 확인");
            //  userDetails.getAuthorities()하면 USER 권한을 확인할 수 있음
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // authentication객체에 웹요청에 대한 세부 정보를 전달함
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            HttpSession session = request.getSession(true);
            // 현재 HTTP 요청으로부터 세션을 가져옴(이때 true는 세션이 없을 경우 새로 생성하라는 의미)
            // 이러한 HTTPsession 객체로 로그인을 관리할 수 있도록 할 것
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,SecurityContextHolder.getContext());
            System.out.println("세션에 저장하는 카카오 토큰 값"+accessToken);
            session.setAttribute("kakaoToken",accessToken);
            mav.setViewName("redirect:/loginsuc");
            return mav;
        }else if(member==null){
//            System.out.println("비회원은 회원가입으로");
            // 등록된 회원이 아니라면 해당 정보들을 가지고 회원가입 처리로 넘길 것이다.

            memberDTO mplus = new memberDTO();
            mplus.setName(name);
            mplus.setPhone_num(phone);
            mplus.setEmail(email);
            mplus.setAge(age);
            mplus.setAuth_name("KAKAO");
            if(gender.equals("male")){
                mplus.setGender("남");
            }else{
                mplus.setGender("여");
            }

//            System.out.println("비회원은 회원가입으로");
            mav.addObject("member", mplus);
            mav.setViewName("/kakaomplus");

            return mav;
        }
        mav.setViewName("redirect:/login");
        return mav;
    }
//
//    @Autowired
//    private final KakaoApi kakaoApi;
//
//
//    @RequestMapping("/oauth2/login")
//    public void kakaologin(@RequestParam("code") String code, Model model, HttpSession session) {
//
//        System.out.println("redirectURL위치로 들어옴");
//
//        ModelAndView mav = new ModelAndView();
//        memberDTO memberdto = new memberDTO();
//        // redirect에 code를 같이 설정해줌으로써 인가에 대한 인증코드를 함께 받을 수 있다.
//
//        // Step1. 인가 코드 받기(@RequestParam String code)  ->  받아온 정보 후 진행
//        // Step2. 토큰 받기(사용자의 로그인을 통하여 발급된 인증 code를 통해서,,
//        // 토큰까지 받아야 카카오 로그인이 완료된다.
//        String accessToken = kakaoApi.getAccessToken(code);
//
//        // Strp3. 사용자 정보를 받기(카카오에 accessToken을 제출함으로써 해당 사용자의 정보를 얻는다.)
//        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);
//
//        String name = (String) userInfo.get("name");
//        String email = (String) userInfo.get("email");
//        String phone_num = (String) userInfo.get("phone_number");
//        String age = (String) userInfo.get("age_range");
//        String gender = (String) userInfo.get("gender");
//
//        System.out.println(phone_num);
//        System.out.println(userService.getMemberByphone(phone_num));
//
//        if (name != null) {
//            if (userService.getMemberByphone(phone_num)!=null) {
//                memberdto.setName(name);
//                String userid = userService.getMemberByphone(phone_num);
//                memberdto.setId(userid);
//                // 이미 회원가입이 되어있는 사람이라면, 해당 정보를 넘겨서 PrincipalDet을 생성하여 로그인시킴
//                OAuth2User user = principalOauth2UserService.loadUser(memberdto);
//                System.out.println(user.getName()+"kkk");
//
//                if(user==null){
//                    throw new BadCredentialsException("회원 인증 오류입니다.");
//                }
//                List<GrantedAuthority> authorities = new ArrayList<>();
//                authorities.add(new SimpleGrantedAuthority("USER"));
//
//                Authentication authentication = new UsernamePasswordAuthenticationToken(user, email, authorities);
//                System.out.println(authentication.getName());
//                session.setAttribute("id", userid);
//                session.setAttribute("accessToken", accessToken);
//                mav.setViewName("redirect:/loginsuc");
//                return mav;
//            } else {
//                session.setAttribute("id", id);
//                session.setAttribute("accessToken", accessToken);
//                mav.addObject("name", name);
//                mav.addObject("email", email);
//                mav.setViewName("redirect:/mplus");
//                return mav;
//            }
//
//        }
//
//        mav.setViewName("/login");
//        System.out.println( name + " / " + email);
//
//    }
//
}
