package curevengers.petit_cure.Dao;

import curevengers.petit_cure.Dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface dpBoardMapper {
    public List<dpBoardDTO> selectAll(pageDTO pagedto) throws Exception;

    public List<dpBoardDTO> selectAllGood(pageDTO pagedto) throws Exception;

    public int countAll() throws Exception;

    public void insert(dpBoardDTO dto) throws Exception;

    public dpBoardDTO selectOne(int no) throws Exception;

    public List<dpcommentDTO> selectdpComment(int no) throws Exception;

    public int updateGoodUp(int no);

    public int updateGoodDown(int no);

    public void adddpComment(dpcommentDTO dto) throws Exception;

    public void updatedpBoard(dpBoardDTO dto) throws Exception;

    public void deletedpBoard(int no) throws Exception;

    public List<dpBoardDTO> search(String title, pageDTO pageDTO) throws Exception;

    public void updatedpComment(int commentNo, String content) throws Exception;

    void insertDPAttach(dpboard_attachDTO dpattachDTO);

    List<dpboard_attachDTO> selectdpattach(int no);

    public void deletedpBoardComment(dpcommentDTO dto) throws Exception;

    public List<dpBoardDTO> gooddpList(pageDTO pagedto) throws Exception;

    public List<dpBoardDTO> datedpList(pageDTO pagedto) throws Exception;

    void updatedpReport(int no);

    int alertdpBoard(alertDTO dto);

    void addLike(dpboardLikeDTO dpboardLikeDTO);

    void deleteLike(dpboardLikeDTO dpboardLikeDTO);

    dpboardLikeDTO dpselectLike(dpboardLikeDTO dpboardLikeDTO);
}
