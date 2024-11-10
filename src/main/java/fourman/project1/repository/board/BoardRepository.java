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
}
