package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dao.dpBoardMapper;
import curevengers.petit_cure.Dto.dpBoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class dpBoardServiceImpl implements dpBoardService {

    @Autowired
    dpBoardMapper dpboardmapper;

    @Override
    public List<dpBoardDTO> selectAll() throws Exception {
        return dpboardmapper.selectAll();
    }

    @Override
    public int countAll() throws Exception {
        return dpboardmapper.countAll();
    }

    @Override
    public void insert(dpBoardDTO dto) throws Exception {
        dpboardmapper.insert(dto);
    }
}
