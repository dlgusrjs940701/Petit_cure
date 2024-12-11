package curevengers.petit_cure.Service;


import curevengers.petit_cure.Dto.AuthVO;
import curevengers.petit_cure.Dto.blackListDTO;
import curevengers.petit_cure.Dto.memberDTO;
import curevengers.petit_cure.Dto.myActivityDTO;
import org.springframework.stereotype.Controller;

import java.util.List;

public interface userService {
    public List<memberDTO> getMemberList();
    public memberDTO getMemberById(String id);
    public String getMemberByphone(String phonenumber);
    public void signup(memberDTO memberdto);
    public void edit(memberDTO memberdto);
    public void withdraw(String id);
    public int cofrmID(String id);
    public List<myActivityDTO> getMyActivity(String id);
    public List<myActivityDTO> getMyActivityList(myActivityDTO myactivityDTO); // 마이페이지 리스트 업데이트
    public int updateBlacklist(memberDTO memberdto);    // 해당 계정 정지
    public void addBlacklist(blackListDTO blacklistdto);      // 블랙리스트 사유 저장
}
