package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dao.dpBoardMapper;
import curevengers.petit_cure.Dto.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class dpBoardServiceImpl implements dpBoardService {

    @Autowired
    dpBoardMapper dpboardmapper;

    @Override
    public List<dpBoardDTO> selectAll(pageDTO pagedto) throws Exception {
        return dpboardmapper.selectAll(pagedto);
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
    @Transactional
    public int updateGoodUP(int no) {
        return dpboardmapper.updateGoodUp(no);
    }

    @Override
    public void addLike(dpboardLikeDTO dpboardLikeDTO) {
        dpboardmapper.addLike(dpboardLikeDTO);
    }

    @Override
    @Transactional
    public int updateGoodDown(int no) {
        return dpboardmapper.updateGoodDown(no);
    }

    @Override
    public void deleteLike(dpboardLikeDTO dpboardLikeDTO) {
        dpboardmapper.deleteLike(dpboardLikeDTO);
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

    @Override
    public void updatedpComment(int commentNo, String content) throws Exception {
        dpboardmapper.updatedpComment(commentNo, content);
    }

    @Override
    public void insertDPAttach(dpboard_attachDTO dpattachDTO) {
        dpboardmapper.insertDPAttach(dpattachDTO);
    }

    @Override
    public List<dpboard_attachDTO> getDPAttach(int no) {
        return dpboardmapper.selectdpattach(no);
    }

    @Override
    public void deletedpBoardComment(dpcommentDTO dto) throws Exception {
        System.out.println(dto.toString());
        dpboardmapper.deletedpBoardComment(dto);
    }

    @Override
    public List<dpBoardDTO> gooddpList(pageDTO pagedto) throws Exception{
        return dpboardmapper.gooddpList(pagedto);
    }

    @Override
    public List<dpBoardDTO> datedpList(pageDTO pagedto) throws Exception{
        return dpboardmapper.datedpList(pagedto);
    }

    @Override
    public int alertdpReport(alertDTO alertDTO){
        dpboardmapper.updatedpReport(alertDTO.getNo());
        return dpboardmapper.alertdpBoard(alertDTO);
    }

    @Override       //  좋아요 조회
    public dpboardLikeDTO dpgetBoardLike(dpboardLikeDTO dpboardLikeDTO) {
        return dpboardmapper.dpselectLike(dpboardLikeDTO);
    }
}
