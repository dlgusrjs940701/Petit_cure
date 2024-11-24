package curevengers.petit_cure.Dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class healthCheckDTO {
    private int height;
    private int weight;
    private int AC;
    private String SBP;
    private String DBP;
    private String FBP;
    private String TC;
    private String HDL;
    private String TG;
    private String LDL;
    private String AST;
    private String ALT;

}
