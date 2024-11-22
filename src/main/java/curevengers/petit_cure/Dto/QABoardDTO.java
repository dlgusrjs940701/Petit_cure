package curevengers.petit_cure.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QABoardDTO {
    private String no;
    private String id;
    private String title;
    private String cate;
    private String content;
    private String limited;
    private int good;
    private String date;
}