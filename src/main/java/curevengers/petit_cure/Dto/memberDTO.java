package curevengers.petit_cure.Dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class memberDTO {
    private String name;
    private String age;
    private String gender;
    private String id;
    private String pass;
    private String email;
    private String addr;
    private String auth_name;
    private String jumin1;
    private String jumin2;

    private List<AuthVO> autoList;
}
