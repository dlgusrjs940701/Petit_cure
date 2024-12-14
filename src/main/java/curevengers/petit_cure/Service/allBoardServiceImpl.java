package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dao.UserMapper;
import curevengers.petit_cure.Dto.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class allBoardServiceImpl implements allBoardService {

    @Autowired
    UserMapper userMapper;

//    @Override
//    public int totalCountBoard() {
//        return userMapper.cntBoard();
//    }

    @Override
    public List<noticeDTO> getNoticenum() {
        return userMapper.getNoticenum();
    }

    @Override
    public List<noticeDTO> getNotices(pageDTO pagedto) {
        return userMapper.getNotices(pagedto);
    }

    @Override
    public noticeDTO getNotice(String no) {
        return userMapper.noticeOne(no);
    }

    @Override
    public int delnoticeOne(String no) {
        return userMapper.delnoticeOne(Integer.valueOf(no));
    }

    @Override
    public int noticesave(noticeDTO noticedto) {
        return userMapper.noticesave(noticedto);
    }

    @Override
    public void add(testDto dto) {
        System.out.println("서비스창");
        userMapper.insert(dto);
    }

    @Override
    public List<freeBoardDTO> getAllFreeBoards(pageDTO pagedto) {
        try {
            return userMapper.findAllBoards(pagedto);
        } catch (Exception e) {
            // 예외 로그 출력
            e.printStackTrace();
            throw new RuntimeException("자유게시판 조회에 실패했습니다.");
        }
    }

    @Override
    public List<freeBoardDTO> getAllFreeBoardsVisit(pageDTO pagedto) {
        return userMapper.findAllBoardsVisit(pagedto);
    }

    @Override
    public List<QABoardDTO> getAllQABoards(pageDTO pagedto) {
        try {
            return userMapper.findQAAllBoards(pagedto);
        } catch (Exception e) {
            // 예외 로그 출력
            e.printStackTrace();
            throw new RuntimeException("QA게시판 조회에 실패했습니다.");
        }
    }

    @Override
    public List<QABoardDTO> getAllQABoardsGood(pageDTO pagedto) {
        return userMapper.findQAAllBoardsGood(pagedto);
    }

    @Override
    public List<alertDTO> findalertAllBoards(pageDTO pagedto) {
        try {
            return userMapper.findalertAllBoards(pagedto);
        } catch (Exception e) {
            // 예외 로그 출력
            e.printStackTrace();
            throw new RuntimeException("신고글 조회에 실패했습니다.");
        }
    }

    @Override
    public freeBoardDTO getBoardNo(String no) {
        return userMapper.selectOne(no);
    }

    @Override
    public QABoardDTO getQABoardNo(String no) {
        return userMapper.selectQAOne(no);
    }

    @Override
    public void addFreeBoard(freeBoardDTO dto) {

        userMapper.insertFreeBoard(dto);
//        String[] filename = dto.getNewFileName();
//        for(int i=0;i<filename.length;i++) {
//            if(filename!=null){
//                userMapper.insertAttach(filename[i]);
//            }
//        }
    }

    @Override
    public void addQABoard(QABoardDTO dto) {
        userMapper.insertQABoard(dto);
    }

    @Override
    public List<freeBoardDTO> getsearchFreeBoards(String title) {
        return userMapper.findSearchBoards(title);
    }

    @Override
    public List<QABoardDTO> getsearchQABoards(String title) {
        return userMapper.findSearchQABoards(title);
    }

    @Override
    public void updateVisit(int no) {
        userMapper.updateVisit(no);
    }


    // 좋아요 구현
    @Override
    @Transactional
    public int freeupdateGood(int no) {
        return userMapper.freeupdateGood(no);
    }

    @Override
    public void freeaddLike(freeboardLikeDTO freeboardLikeDTO) {
        userMapper.freeaddLike(freeboardLikeDTO);
    }

    @Override
    @Transactional
    public int freeupdateGoodDown(int no) {
        return userMapper.freeupdateGoodDown(no);
    }

    @Override
    public void freedeleteLike(freeboardLikeDTO freeboardLikeDTO) {
        userMapper.freedeleteLike(freeboardLikeDTO);
    }

    @Override       //  좋아요 조회
    public freeboardLikeDTO freegetBoardLike(freeboardLikeDTO freeboardLikeDTO) {
        return userMapper.freeselectLike(freeboardLikeDTO);
    }

    @Override
    @Transactional
    public int qaupdateGood(int no) {
        return userMapper.qaupdateGood(no);
    }

    @Override
    public void qaaddLike(qaboardLikeDTO qaboardLikeDTO) {
        userMapper.qaaddLike(qaboardLikeDTO);
    }

    @Override
    @Transactional
    public int qaupdateGoodDown(int no) {
        return userMapper.qaupdateGoodDown(no);
    }

    @Override
    public void qadeleteLike(qaboardLikeDTO qaboardLikeDTO) {
        userMapper.qadeleteLike(qaboardLikeDTO);
    }

    @Override       //  좋아요 조회
    public qaboardLikeDTO qagetBoardLike(qaboardLikeDTO qaboardLikeDTO) {
        return userMapper.qaselectLike(qaboardLikeDTO);
    }

    @Override
    public int totalCountBoard() {
        return userMapper.cntBoard();
    }

    @Override
    public int totalQACountBoard() {
        return userMapper.cntQABoard();
    }

    @Override
    public void addComment(qacommentDTO dto) {
        userMapper.insertComment(dto);
    }

    @Override
    public void addFreeComment(freecommentDTO dto) {
        userMapper.insertFreeComment(dto);
    }

    @Override
    public List<qacommentDTO> getqaComment(String no) {
        return userMapper.selectQAComment(no);
    }

    @Override
    public List<freecommentDTO> getFreeComment(String no) {
        return userMapper.selectFreeComment(no);
    }

    @Override
    public int alertFreeReport(alertDTO dto) {
        userMapper.updateFreeReport(dto.getNo());
        return userMapper.alertFreeBoard(dto);
    }

    // Q&A 게시판 신고기능
    @Override
    public int alertQAReport(alertDTO alertDTO) {
        userMapper.updateQAReport(alertDTO.getNo());
        return userMapper.alertQAboard(alertDTO);
    }
    // 게시판 신고관련 내용 조회
    @Override
    public List<alertDTO> selectAlertcomment(alertDTO alertDTO) {
        return userMapper.selectAlertcomment(alertDTO);
    }

    // 게시글이 삭제되면 해당글 관련 신고글도 모두 삭제
    @Override
    public void deleteAlert(alertDTO alertDTO) {
        userMapper.deleteAlert(alertDTO);
    }


    @Override
    public void insertAttach(freeboard_attachDTO attachDTO) {
        userMapper.insertAttach(attachDTO);
    }

    @Override
    public void insertQAAttach(qaboard_attachDTO qaattachDTO) {
        userMapper.insertQAAttach(qaattachDTO);
    }

    @Override
    public List<freeboard_attachDTO> getAttach(String no) {
        return userMapper.selectAttach(no);
    }

    @Override
    public List<qaboard_attachDTO> getQAAttach(String no) {
        return userMapper.selectQAAttach(no);
    }

    @Override
    public void deleteBoard(String no) {
        userMapper.deleteBoard(no);
    }

    @Override
    public void deleteQABoard(String no) {
        userMapper.deleteQABoard(no);
    }

    @Override
    public void updateBoard(freeBoardDTO dto) {
        userMapper.updateBoard(dto);
    }

    @Override
    public void updateQABoard(QABoardDTO dto) {
        userMapper.updateQABoard(dto);
    }

    @Override
    public void updateComment(freecommentDTO freecommentdto) {
        userMapper.updateComment(freecommentdto);
    }

    @Override
    public void updateqaComment(qacommentDTO qacommentdto) {
        userMapper.updateqaComment(qacommentdto);

    }

    @Override
    public void deletefreeBoardComment(freecommentDTO freecommentdto) {
        userMapper.deleteComment(freecommentdto);
    }

    @Override
    public void deleteqaBoardComment(qacommentDTO qacommentdto) {
        userMapper.deleteqaComment(qacommentdto);
    }

    @Override
    public List<freeBoardDTO> visitList(pageDTO pagedto) {
        return userMapper.visitList(pagedto);
    }

    @Override
    public List<freeBoardDTO> dateList(pageDTO pagedto) {
        return userMapper.dateList(pagedto);
    }

    @Override
    public List<QABoardDTO> goodQAList(pageDTO pagedto) {
        return userMapper.goodQAList(pagedto);
    }

    @Override
    public List<QABoardDTO> dateQAList(pageDTO pagedto) {
        return userMapper.dateQAList(pagedto);
    }

    // 관리자가 현재 신고내역을 조회
    @Override
    public List<alertDTO> alertList() {
        return userMapper.selectAlert();
    }

    @Override
    public List<QABoardDTO> getAgeQABoards(String ageGroup, pageDTO pagedto) {
        return userMapper.AgeQAList(ageGroup, pagedto);
    }


//    @Override
//    public List<commentDTO> getAllComments(commentDTO dto) {
//        return userMapper.findComment(dto);
//    }


}

