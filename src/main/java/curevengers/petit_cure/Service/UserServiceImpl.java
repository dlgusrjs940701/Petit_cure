package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dao.MemberMapper;
import curevengers.petit_cure.Dto.AuthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import curevengers.petit_cure.Dto.memberDTO;

import java.time.LocalDate;
import java.util.List;

import static org.thymeleaf.util.StringUtils.concat;
import static org.thymeleaf.util.StringUtils.substring;


@Service
public class UserServiceImpl implements userService{

    LocalDate now = LocalDate.now();

    @Autowired
    MemberMapper memberMapper;


    @Override
    public List<memberDTO> getMemberList(){
        return memberMapper.getMemberList();
    }

    @Override
    public memberDTO getMemberById(String id){
        return memberMapper.getMemberByID(id);
    }

    @Override
    public void signup(memberDTO memberdto){
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
        // 비밀번호를 암호화해서 DB에 저장
        memberMapper.insertMember(memberdto);
        memberMapper.insertAuth(memberdto.getId());
    }

    @Override
    public void edit(memberDTO memberdto){      // 회원 정보 수정
        // 비밀번호를 암호화해서 DB에 저장
        memberMapper.updateMember(memberdto);
    }

    @Override
    public void withdraw(String id){        // 회원 탈퇴
        memberMapper.deleteMember(id);
    }


    @Override
    public int cofrmID(String id) {
        return memberMapper.selectID(id).size();
    }
}
