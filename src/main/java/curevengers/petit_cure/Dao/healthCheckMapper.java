package curevengers.petit_cure.Dao;

import curevengers.petit_cure.Dto.healthCheckDTO;
import curevengers.petit_cure.Dto.hospitalDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Mapper
@Repository
public interface healthCheckMapper {
    public void insert(healthCheckDTO dto) throws Exception;

    public healthCheckDTO showOne(healthCheckDTO dto) throws Exception;

    public healthCheckDTO selectOne(String id, String date) throws Exception;

    public List<healthCheckDTO> selectAll(String id) throws Exception;

    public ArrayList<hospitalDTO> selectAllHospital(String h_type) throws Exception;
}
