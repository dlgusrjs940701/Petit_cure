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
    public dpBoardDTO selectOne(int no) throws Exception {
        return dpboardmapper.selectOne(no);
    }

    @Override
    public List<dpcommentDTO> getdpComment(int no) throws Exception {
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
    public void adddpComment(dpcommentDTO dto) throws Exception {
        dpboardmapper.adddpComment(dto);
    }

    @Override
    public void updatedpBoard(dpBoardDTO dto) throws Exception {
        dpboardmapper.updatedpBoard(dto);
    }

    @Override
    public void deletedpBoard(int no) throws Exception {
        dpboardmapper.deletedpBoard(no);
    }

    @Override
    public List<dpBoardDTO> getsearchDPBoards(String title) throws Exception {
        return dpboardmapper.search(title);
    }

}
