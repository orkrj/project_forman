package fourman.project1.service.board;

import fourman.project1.domain.board.Board;
import fourman.project1.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    @Autowired private final BoardRepository boardRepository;

    public List<Board> findBoards() {
        return boardRepository.findBoards();
    }

    public Board findBoardById(Long boardId) {
        return boardRepository.findBoardById(boardId).orElse(null);
    }

    public void createBoard(Board board) {
        boardRepository.createBoard(board);
        boardRepository.findBoardById(board.getBoardId()).orElse(null);
    }

    public void updateBoard(Board board) {
        Board findBoard = boardRepository.findBoardById(board.getBoardId())
                .orElse(null);

        Optional.ofNullable(board.getBoardname())
                .ifPresent(boardname -> findBoard.setBoardname(boardname));

        boardRepository.updateBoard(findBoard);
    }

    public void deleteBoard(Long boardId) {

        boardRepository.deleteBoard(boardId);
    }
}
