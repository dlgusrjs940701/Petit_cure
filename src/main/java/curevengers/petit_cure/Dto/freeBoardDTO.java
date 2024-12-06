package curevengers.petit_cure.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class freeBoardDTO {
    private String no;
    private String id;
    private String title;
    private String cate;
    private String content;
    private int visit;
    private String date;
    private String name;

    private String password;

    private String[] newFileName;

}