package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dto.*;

import java.util.List;

public interface dpBoardService {
    public List<dpBoardDTO> selectAll(pageDTO pagedto) throws Exception;

    public int countAll() throws Exception;

    public void insert(dpBoardDTO dto) throws Exception;

    public dpBoardDTO selectOne(int no) throws Exception;

    public List<dpcommentDTO> getdpComment(int no) throws Exception;

    public void updateGoodUP(int no) throws Exception;

    public void updateGoodDown(int no) throws Exception;

    public void adddpComment(dpcommentDTO dto) throws Exception;

    public void updatedpBoard(dpBoardDTO dto) throws Exception;

    public void deletedpBoard(int no) throws Exception;

    public List<dpBoardDTO> getsearchDPBoards(String title) throws Exception;

    public void updatedpComment(int commentNo, String content) throws Exception;

    void insertDPAttach(dpboard_attachDTO dpattachDTO);

    List<dpboard_attachDTO> getDPAttach(int no);

    public void deletedpBoardComment(dpcommentDTO dto) throws Exception;
}
