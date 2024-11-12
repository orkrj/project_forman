package fourman.project1.controller.post;

import fourman.project1.domain.board.Board;
import fourman.project1.domain.post.Post;
import fourman.project1.domain.post.PostMapper;
import fourman.project1.domain.post.PostRequestDto;
import fourman.project1.domain.post.PostResponseDto;
import fourman.project1.domain.traffic.TrafficRequestDto;
import fourman.project1.domain.user.CustomUserDetails;
import fourman.project1.domain.user.User;
import fourman.project1.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/posts")
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping
    public String findPosts(Model model) {
        // Posts 찾기
        List<Post> posts = postService.findPosts();

        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/{postId}")
    public String findPostById(@PathVariable Long postId, Model model,
                               @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        // Post 찾기
        Post post = postService.findPostById(postId);

        model.addAttribute("post", post);
        model.addAttribute("user", customUserDetails);

        return "detailed-post";
    }

    @GetMapping("/create")
    public String createPost(Model model) {
        model.addAttribute("postRequestDto", new PostRequestDto());
        return "create-post";
    }

    @PostMapping("/create")
    public String createPost(@Validated @ModelAttribute PostRequestDto postRequestDto,
                             BindingResult bindingResult,
                             @ModelAttribute TrafficRequestDto testRequestDto,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails,
                             Model model, RedirectAttributes redirectAttributes) {

        // 바인딩 에러시
        if (bindingResult.hasErrors()) {
            model.addAttribute("postRequestDto", postRequestDto);
            return "create-post";
        }

        // User와 Board 찾기
        User findUser = createUser(customUserDetails);
        Board findBoard = createBoard();

        // Post 생성 및 할당
        Post post = postMapper.postRequestDtoToPost(postRequestDto);

        post.setUser(findUser);
        post.setBoard(findBoard);

        // post 저장 및 응답
        postService.createPost(post);
        PostResponseDto postResponseDto = postMapper.postToPostResponseDto(post);

        return "redirect:/posts";
    }

    @PatchMapping("/{postId}")
    public String updatePost(@PathVariable Long postId,
                             @Validated @ModelAttribute PostRequestDto postRequestDto,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails,
                             BindingResult bindingResult, Model model) {

        // User와 Board 찾기
        User findUser = createUser(customUserDetails);
        Board findBoard = createBoard();

        // Post로 매핑
        Post post = postMapper.postRequestDtoToPost(postRequestDto);
        post.setPostId(postId);
        post.setUser(findUser);
        post.setBoard(findBoard);

        // Post 업데이트
        postService.updatePost(post);

        // PostResponseDto 로 매핑
        PostResponseDto postResponseDto = postMapper.postToPostResponseDto(post);

        // model.addAttribute("post", postResponseDto);
        return "redirect:/";
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Long postId) {
        // Post 삭제
        postService.deletePost(postId);
        return "redirect:/posts";
    }

    private static User createUser(CustomUserDetails customUserDetails) {
        User user = User.builder()
                .userId(customUserDetails.getUserId())
                .username(customUserDetails.getUsername())
                .build();
        return user;
    }

    private static Board createBoard() {
        Board board = new Board(); // boardService.findByBoardId();
        board.setBoardId(1L); // board는 하나만 운용하여 일단 1로 고정
        return board;
    }
}
