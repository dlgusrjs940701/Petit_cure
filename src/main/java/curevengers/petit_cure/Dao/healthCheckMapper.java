package curevengers.petit_cure.Dao;

import curevengers.petit_cure.Dto.healthCheckDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface healthCheckMapper {
    public void insert(healthCheckDTO dto) throws Exception;

    public healthCheckDTO selectOne(String id) throws Exception;

    public List<healthCheckDTO> selectAll(String id) throws Exception;
}
