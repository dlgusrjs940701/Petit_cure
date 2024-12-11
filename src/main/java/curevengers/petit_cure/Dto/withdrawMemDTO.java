package curevengers.petit_cure.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class withdrawMemDTO {
    private String id;
    private String cause;
    private String date;
    private int num;
    private String recent_date;
}
