package fourman.project1.repository.post;

import fourman.project1.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final PostMyBatisMapper postMyBatisMapper;

    public List<Post> findPosts() {
        return postMyBatisMapper.findPosts();
    }

    public Optional<Post> findPostById(Long postId) {
        return postMyBatisMapper.findPostById(postId);
    }

    public void createPost(Post post) {
        postMyBatisMapper.createPost(post);
    }

    public void updatePost(Post findPost) {
        postMyBatisMapper.updatePost(findPost);
    }

    public void deletePost(Long postId) {
        postMyBatisMapper.deletePost(postId);
    }
}
