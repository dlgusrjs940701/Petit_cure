package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dao.MemberMapper;
import curevengers.petit_cure.Dto.memberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static org.thymeleaf.util.StringUtils.concat;
import static org.thymeleaf.util.StringUtils.substring;

@Service
public class memberServiceImpl implements memberService{

    @Autowired
    private MemberMapper memberMapper;

    LocalDate now = LocalDate.now();

    @Override
    public void addmember(memberDTO memberdto) {
        String agejumin = memberdto.getJumin1();
        String genderjumin = memberdto.getJumin2();
        int age;
//        System.out.println(agejumin);
//        System.out.println(genderjumin);
        if(genderjumin.equals("1")||genderjumin.equals("2")){
            age = Integer.valueOf(concat("19",(agejumin.substring(0,2))));
//            System.out.println(age);
            age = now.getYear() - age + 1;
            memberdto.setAge(String.valueOf(age));
            if(genderjumin.equals("1")){
                memberdto.setGender("남");
            } else if (genderjumin.equals("2")) {
                memberdto.setGender("여");
            }
        }else{
            age = Integer.valueOf(concat("20",(substring(agejumin,0,2))));
//            System.out.println(age);
            age = now.getYear() - age + 1;
            memberdto.setAge(String.valueOf(age));
            if(genderjumin.equals("3")){
                memberdto.setGender("남");
            } else if (genderjumin.equals("4")) {
                memberdto.setGender("여");
            }
        }
        memberMapper.insertMember(memberdto);
    }

    @Override
    public int cofrmID(String id) {
        return memberMapper.selectID(id).size();
    }
}
