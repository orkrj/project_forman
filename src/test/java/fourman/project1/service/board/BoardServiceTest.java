package fourman.project1.service.board;

import fourman.project1.domain.board.Board;
import fourman.project1.domain.post.Post;
import fourman.project1.domain.user.User;
import fourman.project1.repository.board.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    private Board board;
    private User user;

    //  테스트 실행 전 Mock 객체 초기화하는 메서드
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample User
        user = User.builder()
                .userId(1L)
                .username("TestUser")
                .password("password1")
                .createdAt(ZonedDateTime.now())
                .build();

        // Sample Posts
        Post post1 = new Post(1L, "Post1", "Content 1", user, null, ZonedDateTime.now(), null, null);
        Post post2 = new Post(2L, "Post2", "Content 2", user, null, ZonedDateTime.now(), null, null);
        Post post3 = new Post(3L, "Post3", "Content 3", user, null, ZonedDateTime.now(), null, null);
        Post post4 = new Post(4L, "Post4", "Content 4", user, null, ZonedDateTime.now(), null, null);

        // Sample Board with Posts
        board = Board.builder()
                .boardId(1L)
                .posts(Arrays.asList(post1, post2, post3, post4))
                .build();

        post1.setBoard(board);
        post2.setBoard(board);
        post3.setBoard(board);
        post4.setBoard(board);
    }

    @Test
    void findBoardById() {
        // Given
        when(boardRepository.findBoardById(1L)).thenReturn(board);

        // When
        Board result = boardService.findBoardById(1L);

        // Then
        // 반환된 Board가 null이 아님
        assertNotNull(result);
        // boardId가 올바른지 확인
        assertEquals(1L, result.getBoardId());
        // 게시판에 4개의 게시글이 포함되어 있는지 확인
        assertEquals(4, result.getPosts().size());
        // 첫 번째  게시물의 작성자 이름이 "TestUser"인지 확인
        assertEquals("TestUser", result.getPosts().get(0).getUser().getUsername());
        // findBoardById()가 BoardRepository에서 한 번 호출되었는지 확인
        verify(boardRepository, times(1)).findBoardById(1L);

    }

    @Test
    void randomPostIdx() {
        // Given
        when(boardRepository.findBoardById(1L)).thenReturn(board);

        // When
        List<Integer> randomIndices = boardService.randomPostIdx();

        // Then
        // 반환된 인덱스 리스트의 크기가 3인지 확인
        assertEquals(3, randomIndices.size());
        // 각 인덱스가 게시물 리스트 범위 내에 있는지 확인
        assertTrue(randomIndices.stream().allMatch(idx -> idx >= 0 && idx < board.getPosts().size()));
        // 반환된 인덱스가 중복되지 않음을 확인
        assertEquals(3, randomIndices.stream().distinct().count());

        // findBoardById()가 BoardRepository에서 한 번 호출되었는지 확인
        verify(boardRepository, times(1)).findBoardById(1L);
    }
}