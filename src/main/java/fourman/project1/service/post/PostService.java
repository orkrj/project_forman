package fourman.project1.service.post;

import fourman.project1.domain.post.Post;
import fourman.project1.domain.post.PostRequestDto;
import fourman.project1.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> findPosts() {
        return postRepository.findPosts();
    }

    public Post findPostById(Long id) {
        return postRepository.findPostById(id).orElseThrow(null);
    }

    @Transactional
    public void createPost(Post post) {
//        Post post = new Post();
//        post.setTitle(postRequestDto.getTitle());
//        post.setBody(postRequestDto.getBody());
        postRepository.createPost(post);
    }

    @Transactional
    public void updatePost(Post post) {
        Post findPost = postRepository.findPostById(post.getPostId()).orElse(null);

        if (findPost != null) {
            Optional.ofNullable(post.getTitle()).ifPresent(title -> findPost.setTitle(title));
            Optional.ofNullable(post.getBody()).ifPresent(body -> findPost.setBody(body));
        }

        postRepository.updatePost(findPost);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deletePost(id);
    }
}