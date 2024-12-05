package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dao.dpCheckMapper;
import curevengers.petit_cure.Dto.dpCheckDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class dpCheckServiceImpl implements dpCheckService {

    @Autowired
    dpCheckMapper mapper;

    @Override
    public void insert(dpCheckDTO dto) throws Exception {
        System.out.println(dto.toString());
        mapper.insert(dto);
    }

    @Override
    public dpCheckDTO showOne(dpCheckDTO dto) throws Exception {
        return mapper.showOne(dto);
    }

    @Override
    public dpCheckDTO selectOne(dpCheckDTO dto) throws Exception {
        return mapper.selectOne(dto);
    }

    @Override
    public List<dpCheckDTO> selectAll(String id) throws Exception {
        return mapper.selectAll(id);
    }
}
