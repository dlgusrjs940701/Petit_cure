package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dao.MemberMapper;
import curevengers.petit_cure.Dto.AuthVO;
import curevengers.petit_cure.Dto.blackListDTO;
import curevengers.petit_cure.Dto.myActivityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import curevengers.petit_cure.Dto.memberDTO;

import java.time.LocalDate;
import java.util.List;

import static org.thymeleaf.util.StringUtils.concat;
import static org.thymeleaf.util.StringUtils.substring;

@Configuration
@Service
public class UserServiceImpl implements userService{

    LocalDate now = LocalDate.now();

    @Autowired
    MemberMapper memberMapper;

    BCryptPasswordEncoder passwordEncoder;        // 생성자를 통해 주입할 것

    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder){

        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<memberDTO> getMemberList(){
        return memberMapper.getMemberList();
    }

    @Override
    public memberDTO getMemberById(String id){
        return memberMapper.getMemberByID(id);
    }

    @Override
    public String getMemberByphone(String phonenumber) {
        String existid = memberMapper.selectphonenum(phonenumber);
        return existid;
    }


    @Override
    public void signup(memberDTO memberdto){
        String agejumin = memberdto.getJumin1();
        String genderjumin = memberdto.getJumin2();
        int age;
//        System.out.println(agejumin);
//        System.out.println(genderjumin);
        if(genderjumin!=null){
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
        }
        // 비밀번호를 암호화해서 DB에 저장
//        System.out.println("비밀번호 암호화중");
        if(memberdto.getPass()!=null){
            System.out.println(memberdto.getPass()+"(암호화 전 비밀번호)");
            String pass = passwordEncoder.encode(memberdto.getPass());
            memberdto.setPass(pass);
            System.out.println(pass+"(암호화 된 비밀번호)");
        }
        memberMapper.insertMember(memberdto);
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

    @Override
    public List<myActivityDTO> getMyActivity(String id) {
        return memberMapper.getMyActivity(id);
    }


    // 블랙리스트 대상자 추가 및 정지
    @Override
    public int updateBlacklist(memberDTO memberdto) {
        return memberMapper.updateBlacklist(memberdto);
    }
    // 블랙리스트 사유 저장
    @Override
    public void addBlacklist(blackListDTO blacklistdto) {
        memberMapper.addBlacklist(blacklistdto);
    }




}
