package curevengers.petit_cure.Dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class healthCheckDTO {
    private String id;
    private int height;
    private int weight;
    private int AC;
    private String SBP;
    private String DBP;
    private String FBG;
    private String TC;
    private String HDL;
    private String TG;
    private String LDL;
    private String AST;
    private String ALT;
    private String date;
}
