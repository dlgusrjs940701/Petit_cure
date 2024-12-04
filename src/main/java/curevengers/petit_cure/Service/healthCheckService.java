package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dto.healthCheckDTO;
import curevengers.petit_cure.Dto.hospitalDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public interface healthCheckService {
    public void insert(healthCheckDTO dto) throws Exception;

    public  healthCheckDTO showOne(healthCheckDTO dto) throws Exception;

    public healthCheckDTO selectOne(healthCheckDTO dto) throws Exception;

    public List<healthCheckDTO> selectAll(String id) throws Exception;

    public ArrayList<hospitalDTO> mappingHospital(String h_type) throws Exception;
}
