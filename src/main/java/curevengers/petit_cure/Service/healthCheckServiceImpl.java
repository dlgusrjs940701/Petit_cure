package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dao.healthCheckMapper;
import curevengers.petit_cure.Dto.healthCheckDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class healthCheckServiceImpl implements healthCheckService {

    @Autowired
    private healthCheckMapper hcMapper;

    @Override
    public healthCheckDTO healthCheckResult(healthCheckDTO dto) throws Exception {
        hcMapper.insert(dto);
        return dto;
    }
}
