package curevengers.petit_cure.kakaoapi;

import com.fasterxml.jackson.core.JsonFactory;
import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import org.apache.juli.logging.Log;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

@Getter
@Setter
@Configuration
public class KakaoApi {

    // kakao class 작성
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoapiKey;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoredirectUri;


    // 사용자가 카카오 로그인을 성공하면 카카오측에서 인가 코드를 받아옴
    // 그 코드를 이용하여 accessToken을 반환
    public String getAccessToken(String code) {
        String access_token = null;
        String refresh_token = null;
        String reqUrl = "https://kauth.kakao.com/oauth/token";          // 확인 필요
        // 인증을 받은 코드를 통하여 토큰을 요청할 url의 정보를 선언, 저장

        try {
            URL url = new URL(reqUrl);
            // HttpURLConnection 타입인 conn 변수명의 객체를 생성, url연결(웹페이지 URL 연결)
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // reUrl에 저장된 값을 기반으로 실제 카카오에 보내서 토큰을 받아올 url을 형성하는 작업을 진행
            // 프로토콜이 http://인 경우 반환된 객체를 HttpURLConnection객체로 캐스팅
            // openConnection은 실제 네트워크 연결을 설정하지 않고, 단지 객체를 반환한다.(인스턴스)
            // 네트워크 연결은 connect()메서드가 호출될 때 명시적으로 이루어지거나, 헤더를 읽거나 입력스트림/출력스트림을 가져올 때 암시적으로 동작

            // 먼저, url의 헤더를 세팅
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.setDoOutput(true);         // 이는 outputstream으로 post 데이터를 넘겨주겠다는 뜻
            // URLConnection이 서버에 데이터를 보내는데 사용할 수 있는지 여부를 설정하는 것 -> true로 지정,,

            // 여기서 outputstream은 바이트 단위 입출력을 위한 최상위 입출력 스트림 클래스이다.
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//            BufferedWriter, BufferedReader
//                            -> 입력받은 데이터가 String으로 고정, 입력받은 데이터를 원하는 타입으로 가공하는 작업이 필요
//                            -> 수행시간이 Scanner보다 짧고, 버퍼사이즈도 8배정도로 크기에 입력이 많을 때 유리, 동기화되어 멀티쓰레드에서 안전
//                                BufferedWriter : 문자 기반의 보조 스트림으로 버퍼를 이용해 입출력의 효율을 높일 수 있도록 해주는 역할
//                                        -> 한 바이트씩 url의 String 정보를 입출력하는 것 보다는 버퍼(즉, 바이트 배열)를 이용해서 한번에 여러 바이트를 입출력
//                            -> 처리의 속도면에서 다른 입출력 함수에 비해 월등하게 빠르다
//                            -> 이를 사용하지 않는 입력은 키도의 입력이 키를 누르는 즉시 바로 프로그램에 전달
//                                    but, 이를 사용하면 키보드의 입력이 있을 때마다 한문자씩 버퍼로 전송, 버퍼의 내용을 한번에 프로그램에 전달
//                                    (버퍼가 가득참 or 개행문자 나타남 의 경우)
            StringBuilder sb = new StringBuilder();

            // 필수 쿼리 파라미터 세팅(키+리다이렉트+인증코드)
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=").append(kakaoapiKey);
            sb.append("&redirect_uri=").append(kakaoredirectUri);
            sb.append("&code=").append(code);

            bw.write(sb.toString());
            bw.flush();

            // 응답 코드를 작성
            int responseCode = conn.getResponseCode();
            System.out.println(responseCode+" / kakaoApi로 받아온 토큰 코드");
            
            
            // 응답 코드를 읽기 위해 BufferedReader 객체 생성
            BufferedReader br;
            if (responseCode >= 200 &&  responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            // 읽은 코드를 StringBuilder를 통하여 연결해줌
            String line = "";
            StringBuilder responseSb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseSb.append(line);
            }
            // 최종적으로 연결 완료된 응답 코드를 result변수에 저장
            String result = responseSb.toString();
            System.out.println(result+" / 최종 ResponseBody");
            

            // JsonObject => {{},{},[{},{},{}]}
            // JsonArray => [{},{},{}]
            // JsonElement => {}
            // 즉, JsonObject는 JsonArray와 JsonElement를 합친 개념으로 존재
            // {}, [] 들이 섞여 있고, Key와 Data가 쌍으로 이루어져 존재
            // Json으로 parsing하여 return값으로 응답 토큰 전송
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            access_token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();

            br.close();
            bw.close();


        } catch (MalformedURLException e) {     // URL 형식이 잘못된 경우를 위한 예외처리
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return access_token;
    }

    // accessToken을 받은 kakao가 회원정보를 전송해주는 것을 저장, User정보로 반환하여 회원 정보 저장
    public HashMap<String, Object> getUserInfo(String accessToken) {
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqUrl = "https://kapi.kakao.com/v2/user/me";
        try{
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            int responseCode = conn.getResponseCode();
            System.out.println(responseCode +" / 응답된 회원 정보");

            BufferedReader br;
            if (responseCode >= 200 &&  responseCode <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }else{
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String line = "";
            StringBuilder responseSb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseSb.append(line);
            }
            String result = responseSb.toString();
            System.out.println(result +" / 응답 코드 읽기 완료");
            
            
            // Json으로 파싱
            JsonParser jsonParser = new JsonParser();
            JsonElement element = jsonParser.parse(result);

//            JsonObject userid = element.getAsJsonObject().getAsJsonObject("id").getAsJsonObject();
//            JsonObject propertis = element.getAsJsonObject().getAsJsonObject("properties").getAsJsonObject();
            JsonObject kakaoAccount = element.getAsJsonObject().getAsJsonObject("kakao_account").getAsJsonObject();

//            String id = userid.toString();
//            String name = propertis.getAsJsonObject().get("nickname").getAsString();
            String name = kakaoAccount.getAsJsonObject().get("name").getAsString();
            String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
            String phone_number = kakaoAccount.getAsJsonObject().get("phone_number").getAsString();
            String age_range = kakaoAccount.getAsJsonObject().get("age_range").getAsString();
            String gender = kakaoAccount.getAsJsonObject().get("gender").getAsString();

            userInfo.put("name", name);
            userInfo.put("email",email);
            userInfo.put("phone_number",phone_number);
            userInfo.put("age_range",age_range);
            userInfo.put("gender",gender);
            // 이 id를 이용하여 기존에 회원가입이 된 회원인지를 비교하여
            // 이미 회원가입이 된 회원이라면, 로그인을(client에게 jwt를 제공),
            // 회원가입이 되지 않은 회원이라면 회원가입을 요청하게 구현할 것


//            userInfo.put("email", email);

            System.out.println(name+" / "+email+" / "+phone_number+" / "+age_range+" / "+gender+" 카카오로부터 받아서 userinfo에 저장함");

            br.close();

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return userInfo;
    }

    // accessToken을 받아서 로그아웃 시키는 메서드
    public void kakaoLogout(String accessToken){
        String reqUrl = "https://kapi.kakao.com/v1/user/unlink";         // 확인필요
        System.out.println(accessToken+"카카오로그아웃으로 요청하는 토큰값******************");
        try{
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            System.out.println(conn +"/ 카카오 로그아웃 요청 1");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            System.out.println(conn.getHeaderField("Authorization") +"/ 카카오 로그아웃 요청 url 확인");
            int responseCode = conn.getResponseCode();
            System.out.println(responseCode+" / 카카오 로그아웃 응답코드");
            
            BufferedReader br;
            if (responseCode >= 200 &&  responseCode <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }else{
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String line = "";
            StringBuilder responseSb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseSb.append(line);
            }
            String result = responseSb.toString();
            System.out.println(result+" / 카카오 로그아웃 responseBody부분");

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
