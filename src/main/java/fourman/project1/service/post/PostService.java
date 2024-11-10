package fourman.project1.service.post;

import fourman.project1.domain.post.Post;
import fourman.project1.domain.post.PostRequestDto;
import fourman.project1.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void createPost(Post post) {
        postRepository.createPost(post);
    }

    public void updatePost(Post post) {
        Post findPost = postRepository.findPostById(post.getPostId()).orElse(null);

        if (findPost != null) {
            Optional.ofNullable(post.getTitle()).ifPresent(findPost::setTitle);
            Optional.ofNullable(post.getBody()).ifPresent(findPost::setBody);
        }

        postRepository.updatePost(findPost);
    }

    public void deletePost(Long id) {
        postRepository.deletePost(id);
    }
}