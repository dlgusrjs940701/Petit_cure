package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dto.healthCheckDTO;
import org.springframework.stereotype.Service;


public interface healthCheckService {
    public healthCheckDTO healthCheckResult(healthCheckDTO dto) throws Exception;
}
