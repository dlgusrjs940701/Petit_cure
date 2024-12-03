package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dto.dpCheckDTO;
import curevengers.petit_cure.Dto.healthCheckDTO;

import java.util.List;

public interface dpCheckService {
    public void insert(dpCheckDTO dto) throws Exception;

    public dpCheckDTO showOne(dpCheckDTO dto) throws Exception;

    public dpCheckDTO selectOne(String id, String date) throws Exception;

    public List<dpCheckDTO> selectAll(String id) throws Exception;
}
