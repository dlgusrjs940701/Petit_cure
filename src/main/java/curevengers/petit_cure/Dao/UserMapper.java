package curevengers.petit_cure.Dao;

import curevengers.petit_cure.Dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    //    @Insert("Insert Into 연결test values (#{id})") void insert(String id);
    public void insert(testDto dto);

    List<freeBoardDTO> findAllBoards(pageDTO pagedto);

    List<QABoardDTO> findQAAllBoards(pageDTO pagedto);

    freeBoardDTO selectOne(String no);

    QABoardDTO selectQAOne(String no);

    void insertFreeBoard(freeBoardDTO dto);

    void insertQABoard(QABoardDTO dto);

    List<freeBoardDTO> findSearchBoards(String title);

    List<QABoardDTO> findSearchQABoards(String title);

    void updateVisit(int no);


    void updateGood(int no);

    void updateGoodDown(int no);


    int cntBoard();

    int cntQABoard();

    void insertComment(qacommentDTO dto);

    void insertFreeComment(freecommentDTO dto);
    List<qacommentDTO> selectQAComment(String no);

    List<freecommentDTO> selectFreeComment(String no);

    int cntdepBoard();

    void updateReport(int no);

    void updateQAReport(int no);

    void insertAttach(freeboard_attachDTO attachDTO);


    // Q&A게시판 글 수정
    void updateQABoard(QABoardDTO dto);

    // 자유게시판 글 삭제
    void deleteBoard(String no);

    // Q&A게시판 글 삭제
    void deleteQABoard(String no);

    // 자유게시판 글 수정
    void updateBoard(freeBoardDTO dto);

    void insertQAAttach(qaboard_attachDTO qaattachDTO);

    List<freeboard_attachDTO> selectAttach(String no);

    List<qaboard_attachDTO> selectQAAttach(String no);

}
