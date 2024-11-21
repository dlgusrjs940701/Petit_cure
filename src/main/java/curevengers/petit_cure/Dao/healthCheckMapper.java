package curevengers.petit_cure.Dao;

import curevengers.petit_cure.Dto.healthCheckDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface healthCheckMapper {
    public healthCheckDTO insert(healthCheckDTO dto);
}
