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
   public String findBoardById(Model model) {
      Board findBoard = boardService.findBoardById((long) 1);

      List<Integer> randomIdx = boardService.randomPostIdx();

      Post first = findBoard.getPosts().get(randomIdx.get(0));
      Post second = findBoard.getPosts().get(randomIdx.get(1));
      Post third = findBoard.getPosts().get(randomIdx.get(2));

      model.addAttribute("board", findBoard);
      model.addAttribute("first", first);
      model.addAttribute("second", second);
      model.addAttribute("third", third);

      return "board";
   }
}
