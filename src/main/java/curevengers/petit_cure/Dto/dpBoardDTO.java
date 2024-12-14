package curevengers.petit_cure.Dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class dpBoardDTO {
    private int no;
    private String id;
    private String title;
    private String content;
    private String date;
    private int good;
    private String password;
    private String[] newFileName;
    private String cate;
    private String fileExist;
}
