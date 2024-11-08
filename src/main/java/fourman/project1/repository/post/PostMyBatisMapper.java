package fourman.project1.repository.post;

import fourman.project1.domain.post.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMyBatisMapper {
    List<Post> findPosts();
    Optional<Post> findPostById(Long postId);
    void createPost(Post post);
    void updatePost(Post post);
    void deletePost(Long postId);
}
