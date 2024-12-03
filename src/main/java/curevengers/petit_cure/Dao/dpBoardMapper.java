package curevengers.petit_cure.Dao;

import curevengers.petit_cure.Dto.dpBoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface dpBoardMapper {
    public List<dpBoardDTO> selectAll() throws Exception;

    public int countAll() throws Exception;

    public void insert(dpBoardDTO dto) throws Exception;
}
