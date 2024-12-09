package curevengers.petit_cure.Dao;

import curevengers.petit_cure.Dto.dpBoardDTO;
import curevengers.petit_cure.Dto.dpboard_attachDTO;
import curevengers.petit_cure.Dto.dpcommentDTO;
import curevengers.petit_cure.Dto.pageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface dpBoardMapper {
    public List<dpBoardDTO> selectAll(pageDTO pagedto) throws Exception;

    public int countAll() throws Exception;

    public void insert(dpBoardDTO dto) throws Exception;

    public dpBoardDTO selectOne(int no) throws Exception;

    public List<dpcommentDTO> selectdpComment(int no) throws Exception;

    public void updateGoodUp(int no) throws Exception;

    public void updateGoodDown(int no) throws Exception;

    public void adddpComment(dpcommentDTO dto) throws Exception;

    public void updatedpBoard(dpBoardDTO dto) throws Exception;

    public void deletedpBoard(int no) throws Exception;

    public List<dpBoardDTO> search(String title) throws Exception;


    public void updatedpComment(int commentNo, String content) throws Exception;

    void insertDPAttach(dpboard_attachDTO dpattachDTO);

    List<dpboard_attachDTO> selectdpattach(int no);

}
