package curevengers.petit_cure.Dao;

import curevengers.petit_cure.Dto.*;
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
    public int updateMember(memberDTO memberdto);  // 회원 정보 수정
    public int deleteMember(String id);    // 회원 탈퇴
    public void addWithdraw(withdrawMemDTO withdrawMember);    // 탈퇴 사유 저장
    List<withdrawMemDTO> selectWithdraw(pageDTO pagedto);  // 탈퇴 현황 조회 - 페이징까지
    List<withdrawMemDTO> countWithdraw();   //  탈퇴 현황 총 갯수
    List<withdrawMemDTO> withdrawCause(String cause);       // 탈퇴 사유별 현황 조회
    public int deleteWithdraw(String cause);
    public List<myActivityDTO> getMyActivity(String id);
    public List<myActivityDTO> getMyActivityListFree(myActivityDTO myActivityDTO);
    public List<myActivityDTO> getMyActivityListQA(myActivityDTO myActivityDTO);
    public List<myActivityDTO> getMyActivityListDP(myActivityDTO myActivityDTO);
//    public int insertAuth(AuthVO autoVO);
    int updateBlacklist(memberDTO memberdto);
    public void addBlacklist(blackListDTO blacklistdto);
    public blackListDTO selectBlack(String id);
}
