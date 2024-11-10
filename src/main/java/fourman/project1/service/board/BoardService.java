package fourman.project1.service.board;

import fourman.project1.domain.board.Board;
import fourman.project1.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> findBoards() {
        return boardRepository.findBoards();
    }
}
