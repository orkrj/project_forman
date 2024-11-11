package fourman.project1.controller.board;

import fourman.project1.domain.board.Board;
import fourman.project1.domain.board.BoardMapper;
import fourman.project1.domain.post.Post;
import fourman.project1.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RequestMapping("/boards")
@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

   private final BoardService boardService;
   private final BoardMapper boardMapper;

   @GetMapping
    public String findBoards(Model model) {
       List<Board> boards = boardService.findBoards();
       model.addAttribute("boards", boards);
       return "";
   }

   @GetMapping("/{boardId}")
   public String findBoardById(@PathVariable Long boardId, Model model) {
      Board findBoard = boardService.findBoardById(boardId);

      // findBoard가 null인 경우 처리
      if (findBoard == null) {
         model.addAttribute("error", "해당 ID의 Board를 찾을 수 없습니다.");
         return "board";  // board 화면으로 돌아가도록 처리
      }

      // Board 안에 있는 Post들을 랜덤으로 3개 선택
      List<Post> posts = findBoard.getPosts();

      if (posts.size() >= 3) {
         Collections.shuffle(posts); // 리스트 무작위 섞기
         posts = posts.subList(0, 3); // 임의의 3개 선택
      } else {
         // 부족한 post 수만큼 null을 추가하여 총 3개 채우기
         while (posts.size() < 3) {
            posts.add(null); // null 추가
         }
      }


      model.addAttribute("board", findBoard);
      model.addAttribute("randomPosts", posts); // 랜덤으로 선택된 post들

      return "board";
   }
}
