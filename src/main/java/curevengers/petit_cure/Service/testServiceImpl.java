package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dao.UserMapper;
import curevengers.petit_cure.Dto.QABoardDTO;
import curevengers.petit_cure.Dto.freeBoardDTO;
import curevengers.petit_cure.Dto.testDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class testServiceImpl implements testService {

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
    public List<freeBoardDTO> getAllFreeBoards() {
        try {
            return userMapper.findAllBoards();
        } catch (Exception e) {
            // 예외 로그 출력
            e.printStackTrace();
            throw new RuntimeException("자유게시판 조회에 실패했습니다.");
        }
    }

    @Override
    public List<QABoardDTO> getAllQABoards() {
        try {
            return userMapper.findQAAllBoards();
        } catch (Exception e) {
            // 예외 로그 출력
            e.printStackTrace();
            throw new RuntimeException("QA게시판 조회에 실패했습니다.");
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


}
