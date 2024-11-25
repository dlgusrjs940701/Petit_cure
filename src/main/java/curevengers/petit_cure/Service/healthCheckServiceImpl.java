package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dao.healthCheckMapper;
import curevengers.petit_cure.Dto.healthCheckDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public healthCheckDTO selectOne(String id) throws Exception {
        healthCheckDTO dto = hcMapper.selectOne(id);
        return dto;
    }

    @Override
    public List<healthCheckDTO> selectAll(String id) throws Exception {
        List<healthCheckDTO> list = hcMapper.selectAll(id);
        return list;
    }
}
