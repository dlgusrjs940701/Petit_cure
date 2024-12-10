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

    @Override
    @Transactional
    public int updateGood(int no) {
        return userMapper.updateGood(no);
    }

    @Override
    public void addLike(boardLikeDTO boardLikeDTO) {
        userMapper.addLike(boardLikeDTO);
    }

    @Override
    @Transactional
    public int updateGoodDown(int no) {
        return userMapper.updateGoodDown(no);
    }

    @Override
    public void deleteLike(boardLikeDTO boardLikeDTO) {
        userMapper.deleteLike(boardLikeDTO);
    }

    @Override       //  좋아요 조회
    public boardLikeDTO getBoardLike(boardLikeDTO boardLikeDTO) {
        return userMapper.selectLike(boardLikeDTO);
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
    public void updateReport(int no) {
        userMapper.updateReport(no);
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
    public void updateDPReport(int no) {
        userMapper.updateDPReport(no);
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


//    @Override
//    public List<commentDTO> getAllComments(commentDTO dto) {
//        return userMapper.findComment(dto);
//    }

}

