package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dto.*;

import java.util.List;

public interface allBoardService {


//    int totalCountBoard();
    List<noticeDTO> getNoticenum();
    List<noticeDTO> getNotices(pageDTO pagedto);
    noticeDTO getNotice(String no);
    int delnoticeOne(String no);
    public int noticesave(noticeDTO noticedto);

    public void add(testDto dto);

    //게시판 글 조회
    List<freeBoardDTO> getAllFreeBoards(pageDTO pagedto);

    List<freeBoardDTO> getAllFreeBoardsVisit(pageDTO pagedto);

    List<QABoardDTO> getAllQABoards(pageDTO pagedto);

    List<QABoardDTO> getAllQABoardsGood(pageDTO pagedto);

    List<alertDTO> findalertAllBoards(pageDTO pagedto);

    // 게시판 글 자세히보기
    // 게시판 번호에 따름
    freeBoardDTO getBoardNo(String no);

    QABoardDTO getQABoardNo(String no);

    // 게시판 글 저장
    void addFreeBoard(freeBoardDTO dto);

    void addQABoard(QABoardDTO dto);


    List<freeBoardDTO> getsearchFreeBoards(String title, pageDTO pageDTO);

    List<QABoardDTO> getsearchQABoards(String title, pageDTO pageDTO);

    // 조회수 구현
    public void updateVisit(int no);

    // 좋아요 구현
    int freeupdateGood(int no);
    void freeaddLike(freeboardLikeDTO freeboardLikeDTO);
    // 좋아요 취소 구현
    int freeupdateGoodDown(int no);
    void freedeleteLike(freeboardLikeDTO freeboardLikeDTO);
    // 좋아요 조회
    freeboardLikeDTO freegetBoardLike(freeboardLikeDTO freeboardLikeDTO);

    // 좋아요 구현
    int qaupdateGood(int no);
    void qaaddLike(qaboardLikeDTO qaboardLikeDTO);
    // 좋아요 취소 구현
    int qaupdateGoodDown(int no);
    void qadeleteLike(qaboardLikeDTO qaboardLikeDTO);
    // 좋아요 조회
    qaboardLikeDTO qagetBoardLike(qaboardLikeDTO qaboardLikeDTO);


    int totalCountBoard();

    int totalQACountBoard();

    void addComment(qacommentDTO dto);

    void addFreeComment(freecommentDTO dto);

    List<qacommentDTO> getqaComment(String no);

    List<freecommentDTO> getFreeComment(String no);

    int alertFreeReport(alertDTO dto);

    int alertQAReport(alertDTO alertDTO);       // Q&A 게시판 신고
    List<alertDTO> selectAlertcomment(alertDTO alertDTO);        // 게시판 신고 관련 조회
    void deleteAlert(alertDTO alertDTO);       // 관리자가 Q&A신고글 삭제시 해당 신고내용도 삭제

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

    // 자유게시판 댓글 수정
    void updateComment(freecommentDTO freecommentdto);

    // Q&A게시판 댓글 수정
    void updateqaComment(qacommentDTO qacommentdto);

    // 자유게시판 댓글 삭제
    void deletefreeBoardComment(freecommentDTO freecommentdto);

    // Q&A게시판 댓글 삭제
    void deleteqaBoardComment(qacommentDTO qacommentdto);

    List<freeBoardDTO> visitList(pageDTO pagedto);

    List<freeBoardDTO> dateList(pageDTO pagedto);

    List<QABoardDTO> goodQAList(pageDTO pagedto);

    List<QABoardDTO> dateQAList(pageDTO pagedto);

    List<alertDTO> alertList();
    List<QABoardDTO> getAgeQABoards(String ageGroup, pageDTO pagedto);


//    List<commentDTO> getAllComments(commentDTO dto);
}
