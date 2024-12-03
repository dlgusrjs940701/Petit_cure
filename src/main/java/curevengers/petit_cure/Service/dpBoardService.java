package curevengers.petit_cure.Service;

import curevengers.petit_cure.Dto.dpBoardDTO;

import java.util.List;

public interface dpBoardService {
    public List<dpBoardDTO> selectAll() throws Exception;

    public int countAll() throws Exception;

    public void insert(dpBoardDTO dto) throws Exception;
}
