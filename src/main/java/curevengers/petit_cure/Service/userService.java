package curevengers.petit_cure.Service;


import curevengers.petit_cure.Dto.AuthVO;
import curevengers.petit_cure.Dto.memberDTO;
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
}
