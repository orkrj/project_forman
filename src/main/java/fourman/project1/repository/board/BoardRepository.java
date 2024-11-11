package fourman.project1.repository.board;

import fourman.project1.domain.board.Board;
import fourman.project1.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final BoardMyBatisMapper boardMyBatisMapper;

    public Board findBoardById(Long boardId) {
        return boardMyBatisMapper.findBoardById(boardId);
    }

}
