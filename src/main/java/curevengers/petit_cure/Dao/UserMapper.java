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

    List<alertDTO> findalertAllBoards(pageDTO pagedto);

    freeBoardDTO selectOne(String no);      // 자유게시판 하나 조회

    QABoardDTO selectQAOne(String no);

    void insertFreeBoard(freeBoardDTO dto);

    void insertQABoard(QABoardDTO dto);

    List<freeBoardDTO> findSearchBoards(String title);

    List<QABoardDTO> findSearchQABoards(String title);

    void updateVisit(int no);


    int updateGood(int no);     // update는 성공시에 1을 반환, 실패시에는 0을 반환함
    void addLike(boardLikeDTO boardLikeDTO);


    int updateGoodDown(int no); // update는 성공시에 1을 반환, 실패시에는 0을 반환함;
    void deleteLike(boardLikeDTO boardLikeDTO);
    
    // 좋아요 조회
    boardLikeDTO selectLike(boardLikeDTO boardLikeDTO);

    int cntBoard();

    int cntQABoard();

    void insertComment(qacommentDTO dto);

    void insertFreeComment(freecommentDTO dto);
    List<qacommentDTO> selectQAComment(String no);

    List<freecommentDTO> selectFreeComment(String no);

    int cntdepBoard();

    void updateReport(int no);

    // QA게시판 신고 관련
    void updateQAReport(int no);
    int alertQAboard(alertDTO alertDTO);
    List<alertDTO> selectAlertcomment(alertDTO alertDTO);
    void deleteAlert(alertDTO alertDTO);

    void updateDPReport(int no);

    void insertAttach(freeboard_attachDTO attachDTO);


    // 자유게시판 글 수정
    void updateBoard(freeBoardDTO dto);

    // Q&A게시판 글 수정
    void updateQABoard(QABoardDTO dto);

    // 자유게시판 글 삭제
    void deleteBoard(String no);

    // Q&A게시판 글 삭제
    void deleteQABoard(String no);

    void insertQAAttach(qaboard_attachDTO qaattachDTO);

    List<freeboard_attachDTO> selectAttach(String no);

    List<qaboard_attachDTO> selectQAAttach(String no);

    void updateComment(freecommentDTO dto);

    void updateqaComment(qacommentDTO dto);

    void deleteComment(freecommentDTO dto);

    void deleteqaComment(qacommentDTO dto);

    List<freeBoardDTO> visitList(pageDTO pagedto);

    List<freeBoardDTO> dateList(pageDTO pagedto);

    List<QABoardDTO> goodQAList(pageDTO pagedto);

    List<QABoardDTO> dateQAList(pageDTO pagedto);

    // 관리자가 신고내역을 조회
    List<alertDTO> selectAlert();
}
