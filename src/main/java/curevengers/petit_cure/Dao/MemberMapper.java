package curevengers.petit_cure.Dao;

import curevengers.petit_cure.Dto.AuthVO;
import curevengers.petit_cure.Dto.memberDTO;
import curevengers.petit_cure.Dto.myActivityDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MemberMapper {
    public void insertMember(memberDTO memberdto);      // 회원가입 하기
    public void insertAuth(String id);
    public List<memberDTO> selectID(String id);     // 해당하는 회원 정보를 가져오기

    public String selectphonenum(String phonenumber);

    List<memberDTO> getMemberList();        // 전체 정보 가져오기
    public memberDTO getMemberByID(String username);  // 한명의 정보 가져오기
    public void updateMember(memberDTO memberdto);  // 회원 정보 수정
    public void deleteMember(String id);    // 회원 탈퇴
    public List<myActivityDTO> getMyActivity(String id);
//    public int insertAuth(AuthVO autoVO);
}
