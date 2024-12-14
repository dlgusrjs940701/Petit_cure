package curevengers.petit_cure.Dto;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class noticeDTO {
    private String no;
    private String title;
    private String content;
    private String date;
}
