package curevengers.petit_cure.Dao;

import curevengers.petit_cure.Dto.testDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

//    @Insert("Insert Into 연결test values (#{id})") void insert(String id);
    public void insert(testDto dto);
}
