package fourman.project1.controller.post;

import fourman.project1.domain.board.Board;
import fourman.project1.domain.post.Post;
import fourman.project1.domain.post.PostMapper;
import fourman.project1.domain.post.PostRequestDto;
import fourman.project1.domain.post.PostResponseDto;
import fourman.project1.domain.traffic.TrafficRequestDto;
import fourman.project1.domain.user.User;
import fourman.project1.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/posts")
@Controller
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping
    public String findPosts(Model model) {
        List<Post> posts = postService.findPosts();
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/{postId}")
    public String findPostById(@PathVariable Long postId, Model model) {
        Post post = postService.findPostById(postId);
        model.addAttribute("post", post);
        return "detailed-post";
    }

    @GetMapping("/create")
    public String createPost(Model model) {
        return "create-post";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute PostRequestDto postRequestDto,
                             @ModelAttribute TrafficRequestDto testRequestDto,
                             Model model, RedirectAttributes redirectAttributes) {

        User findUser = new User();
        Board findBoard = new Board();

        Post post = Post.from(postRequestDto);
        post.setUser(findUser);
        post.setBoard(findBoard);

        postService.createPost(post);
        PostResponseDto postResponseDto = postMapper.postToPostResponseDto(post);

        return "redirect:/posts";
    }

    @PatchMapping("/{postId}")
    public String updatePost(@PathVariable Long postId, @ModelAttribute PostRequestDto postRequestDto, Model model) {

        User findUser = new User();
        Board findBoard = new Board();

        Post post = postMapper.postRequestDtoToPost(postRequestDto);
        post.setPostId(postId);
        post.setUser(findUser);
        post.setBoard(findBoard);

        postService.updatePost(post);
        PostResponseDto postResponseDto = postMapper.postToPostResponseDto(post);

        return "redirect:/";
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/";
    }
}
