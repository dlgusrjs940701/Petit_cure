package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dao.UserMapper;
import curevengers.petit_cure.Dto.testDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class testServiceImpl implements testService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(testDto dto) {
        System.out.println("서비스창");
        userMapper.insert(dto);
    }
}
