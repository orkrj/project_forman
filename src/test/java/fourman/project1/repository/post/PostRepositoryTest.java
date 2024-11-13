package fourman.project1.repository.post;

import fourman.project1.domain.board.Board;
import fourman.project1.domain.post.Post;
import fourman.project1.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void findPosts() {
        //given

        //when
        List<Post> posts = postRepository.findPosts();

        //then
        assertThat(posts.size()).isEqualTo(300);
    }

    @Test
    void findPostById() {
        //given
        Post post = new Post();
        post.setTitle("title1");
        post.setBody("body1");
        post.setUser(new User());
        post.setBoard(new Board());

        postRepository.createPost(post);

        //when
        Optional<Post> findPost = postRepository.findPostById(301L);

        //then
        assertThat(findPost.isPresent()).isTrue();
        assertThat(findPost.get().getPostId()).isEqualTo(301L);
        assertThat(findPost.get().getTitle()).isEqualTo("title1");
        assertThat(findPost.get().getBody()).isEqualTo("body1");
    }

    @Test
    void createPost() {
        //given
        Post post = new Post();
        post.setTitle("title");
        post.setBody("body");
        post.setUser(new User());
        post.setBoard(new Board());

        //when
        postRepository.createPost(post);

        //then
        assertThat(post.getPostId()).isEqualTo(301L); // 현재 더미데이터로 post를 300개 추가하는 코드가 있음
    }

    @Test
    void updatePost() {
        //given
        Post post = postRepository.findPostById(1L).get();
        post.setTitle("title111");
        post.setBody("body111");

        //when
        postRepository.updatePost(post);

        //then
        Post findpost = postRepository.findPostById(1L).get();
        assertThat(findpost.getTitle()).isEqualTo("title111");
        assertThat(findpost.getBody()).isEqualTo("body111");

    }

    @Test
    void deletePost() {
        //given
        Post post = new Post();
        post.setTitle("title");
        post.setBody("body");
        post.setUser(new User());
        post.setBoard(new Board());
        postRepository.createPost(post);

        Long deletePostId = postRepository.findPostById(301L).get().getPostId();

        //when
        postRepository.deletePost(deletePostId);
        Optional<Post> optionalPost = postRepository.findPostById(301L);

        //then
        assertThat(post.getPostId()).isEqualTo(301L);
        assertThat(deletePostId).isEqualTo(301L);
        assertThat(optionalPost.isPresent()).isFalse();

    }
}