package curevengers.petit_cure.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Message {
    String message = "";        // alert창에 보여줄 메시지
    String href = "";       // 이동해야할 화면을 설정

    public Message(String message, String href) {
        this.message = message;
        this.href = href;
    }
}

//@Getter
//@AllArgsConstructor
//public class messageDTO {
//    private String message;     // 사용자에게 전달할 메시지
//    private String redirectUri;     // 리다이렉트 URI
//    private RequestMethod method;       // HTTP 요청 메서드
//        // RequestMethod는 spring-web 라이브러리에 포함된 enum(상수처리용) 클래스
//    private Map<String, Object> data;       // 화면(view)으로 전달할 데이터(파라미터)
//        // 페이지별로 전달할 파라미터의 개수가 랜덤하기 때문에 여러 데이터를 key-value 형태로 담을 수 있는 Map을 이용
//
//}

