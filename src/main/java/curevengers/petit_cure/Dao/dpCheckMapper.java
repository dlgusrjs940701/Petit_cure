package curevengers.petit_cure.Dao;

import curevengers.petit_cure.Dto.dpCheckDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface dpCheckMapper {
    public void insert(dpCheckDTO dto);

    public dpCheckDTO showOne(dpCheckDTO dto);

    public dpCheckDTO selectOne(dpCheckDTO dto);

    public List<dpCheckDTO> selectAll(String id);

}
