package curevengers.petit_cure.Dao;

import curevengers.petit_cure.Dto.memberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberMapper {
    public void insertMember(memberDTO memberdto);      // 회원가입 하기
    public List<memberDTO> selectID(String id);     // 해당하는 회원 정보를 가져오기

    List<memberDTO> getMemberList();        // 전체 정보 가져오기
    public memberDTO getMemberByID(String id);  // 한명의 정보 가져오기
    public void updateMember(memberDTO memberdto);  // 회원 정보 수정
    public void deleteMember(String id);    // 회원 탈퇴

}
