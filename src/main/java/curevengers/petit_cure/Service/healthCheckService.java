package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dto.healthCheckDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface healthCheckService {
    public void insert(healthCheckDTO dto) throws Exception;

    public healthCheckDTO selectOne(String id) throws Exception;

    public List<healthCheckDTO> selectAll(String id) throws Exception;
}
