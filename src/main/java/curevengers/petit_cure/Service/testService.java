package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dto.freeBoardDTO;
import curevengers.petit_cure.Dto.testDto;

import java.util.List;

public interface testService {
    public void add(testDto dto);

    //게시판 글 조회
    List<freeBoardDTO> getAllFreeBoards();

    // 게시판 글 자세히보기
    // 게시판 번호에 따름
    freeBoardDTO getBoardNo(String no);


}
