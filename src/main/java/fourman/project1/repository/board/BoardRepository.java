package fourman.project1.repository.board;

import fourman.project1.domain.board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final BoardMyBatisMapper boardMyBatisMapper;

    public List<Board> findBoards() {
        return boardMyBatisMapper.findBoards();
    }

    public Optional<Board> findBoardById(Long boardId) {
        return boardMyBatisMapper.findBoardById(boardId);
    }

    public void createBoard(Board board) {
        boardMyBatisMapper.createBoard(board);
    }

    public void updateBoard(Board board) {
        boardMyBatisMapper.updateBoard(board);
    }
    public void deleteBoard(Long boardId) {
        boardMyBatisMapper.deleteBoard(boardId);
    }
}
