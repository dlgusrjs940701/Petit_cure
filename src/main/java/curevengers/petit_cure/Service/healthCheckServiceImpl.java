package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dao.healthCheckMapper;
import curevengers.petit_cure.Dto.healthCheckDTO;
import curevengers.petit_cure.Dto.hospitalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class healthCheckServiceImpl implements healthCheckService {

    @Autowired
    private healthCheckMapper hcMapper;

    @Override
    public void insert(healthCheckDTO dto) throws Exception {
        hcMapper.insert(dto);
    }

    @Override
    public healthCheckDTO showOne(healthCheckDTO dto) throws Exception {
        healthCheckDTO dto1 = hcMapper.showOne(dto);
        return dto1;
    }

    @Override
    public healthCheckDTO selectOne(String id, String date) throws Exception {
        healthCheckDTO dto = hcMapper.selectOne(id, date);
        return dto;
    }

    @Override
    public List<healthCheckDTO> selectAll(String id) throws Exception {
        List<healthCheckDTO> list = hcMapper.selectAll(id);
        return list;
    }

    @Override
    public ArrayList<hospitalDTO> mappingHospital(String h_type) throws Exception {
        ArrayList<hospitalDTO> list = hcMapper.selectAllHospital(h_type);
        return list;
    }
}
