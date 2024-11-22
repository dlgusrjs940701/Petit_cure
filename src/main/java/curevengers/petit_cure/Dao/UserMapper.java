package curevengers.petit_cure.Dao;

import curevengers.petit_cure.Dto.QABoardDTO;
import curevengers.petit_cure.Dto.freeBoardDTO;
import curevengers.petit_cure.Dto.testDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    //    @Insert("Insert Into 연결test values (#{id})") void insert(String id);
    public void insert(testDto dto);

    List<freeBoardDTO> findAllBoards();

    List<QABoardDTO> findQAAllBoards();

    freeBoardDTO selectOne(String no);

    QABoardDTO selectQAOne(String no);

    void insertFreeBoard(freeBoardDTO dto);

    void insertQABoard(QABoardDTO dto);

    List<freeBoardDTO> findSearchBoards(String title);

    List<QABoardDTO> findSearchQABoards(String title);
}
