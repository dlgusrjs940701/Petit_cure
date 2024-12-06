package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dto.*;

import java.util.List;

public interface testService {


//    int totalCountBoard();

    public void add(testDto dto);

    //게시판 글 조회
    List<freeBoardDTO> getAllFreeBoards(pageDTO pagedto);

    List<QABoardDTO> getAllQABoards(pageDTO pagedto);

    // 게시판 글 자세히보기
    // 게시판 번호에 따름
    freeBoardDTO getBoardNo(String no);

    QABoardDTO getQABoardNo(String no);

    // 게시판 글 저장
    void addFreeBoard(freeBoardDTO dto);

    void addQABoard(QABoardDTO dto);


    List<freeBoardDTO> getsearchFreeBoards(String title);

    List<QABoardDTO> getsearchQABoards(String title);

    // 조회수 구현
    public void updateVisit(int no);

    // 좋아요 구현
    void updateGood(int no);

    // 좋아요 취소 구현
    void updateGoodDown(int no);


    int totalCountBoard();

    int totalQACountBoard();

    void addComment(qacommentDTO dto);

    void addFreeComment(freecommentDTO dto);

    List<qacommentDTO> getqaComment(String no);

    List<freecommentDTO> getFreeComment(String no);

    void updateReport(int no);

    void updateQAReport(int no);

    void insertAttach(freeboard_attachDTO attachDTO);

    void insertQAAttach(qaboard_attachDTO qaattachDTO);

    List<freeboard_attachDTO> getAttach(String no);

    List<qaboard_attachDTO> getQAAttach(String no);

    // 자유게시판 글 삭제
    void deleteBoard(String no);

    // Q&A게시판 글 삭제
    void deleteQABoard(String no);

    // 자유게시판 글 수정
    void updateBoard(freeBoardDTO dto);

    // Q&A게시판 글 수정
    void updateQABoard(QABoardDTO dto);

//    List<commentDTO> getAllComments(commentDTO dto);
}
