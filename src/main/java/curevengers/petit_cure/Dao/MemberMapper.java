package curevengers.petit_cure.Dao;

import curevengers.petit_cure.Dto.memberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberMapper {
    public void insertMember(memberDTO memberdto);
    public List<memberDTO> selectID(String id);
}
