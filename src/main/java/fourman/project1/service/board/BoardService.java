package fourman.project1.service.board;

import fourman.project1.domain.board.Board;
import fourman.project1.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Board findBoardById(Long boardId) {
        return boardRepository.findBoardById(boardId);
    }

    public List<Integer> randomPostIdx() {
        Board board = boardRepository.findBoardById((long) 1);

        List<Integer> idx = new ArrayList<>();

        while (idx.size() < 3) {
            int randomIdx = new Random().nextInt(board.getPosts().size());
            if (!idx.contains(randomIdx)) {
                idx.add(randomIdx);
            }
        }

        return idx;
    }
}
