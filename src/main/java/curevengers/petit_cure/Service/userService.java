package curevengers.petit_cure.Service;


import curevengers.petit_cure.Dto.*;
import org.springframework.stereotype.Controller;

import java.util.List;

public interface userService {
    public List<memberDTO> getMemberList();
    public memberDTO getMemberById(String id);
    public String getMemberByphone(String phonenumber);
    public void signup(memberDTO memberdto);
    public int updateMember(memberDTO memberdto);
    public int deleteMember(String id);     // 회원 탈퇴
    public void addWithdraw(withdrawMemDTO withdrawMember);     // 탈퇴사유 저장
    public List<withdrawMemDTO> selectWithdraw(pageDTO pagedto);       // 탈퇴 현황 조회 - 페이징
    public List<withdrawMemDTO> countWithdraw();    // 탈퇴현황 총갯수
    public List<withdrawMemDTO> withdrawCause(String cause);        // 탈퇴 사유별 현황 조회
    public int deleteWithdraw(String cause);
    public int cofrmID(String id);
    public List<myActivityDTO> getMyActivity(String id);
    public List<myActivityDTO> getMyActivityList(myActivityDTO myactivityDTO); // 마이페이지 리스트 업데이트
    public int updateBlacklist(memberDTO memberdto);    // 해당 계정 정지
    public void addBlacklist(blackListDTO blacklistdto);  // 블랙리스트 사유 저장
    public blackListDTO selectBlack(String id);     // 블랙리스트멤버 사유 검색
}
