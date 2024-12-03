package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dao.dpBoardMapper;
import curevengers.petit_cure.Dto.dpBoardDTO;
import curevengers.petit_cure.Dto.dpcommentDTO;
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

    @Override
    public dpBoardDTO getdpBoardNo(String no) throws Exception {
        return dpboardmapper.selectOne(no);
    }

    @Override
    public List<dpcommentDTO> getdpComment(String no) throws Exception {
        return dpboardmapper.selectdpComment(no);
    }

    @Override
    public void updateGoodUP(int no) throws Exception {
        dpboardmapper.updateGoodUp(no);
    }

    @Override
    public void updateGoodDown(int no) throws Exception {
        dpboardmapper.updateGoodDown(no);
    }

    @Override
    public int totalCountBoard() throws Exception {
        return 0;
    }

    @Override
    public void adddpComment(dpcommentDTO dto) throws Exception {
        dpboardmapper.adddpComment(dto);
    }

}
