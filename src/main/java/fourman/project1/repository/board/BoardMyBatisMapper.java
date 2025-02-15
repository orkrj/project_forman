package fourman.project1.repository.board;

import fourman.project1.domain.board.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMyBatisMapper {
    Board findBoardById(Long boardId);

    void createBoard(Board board);
}
