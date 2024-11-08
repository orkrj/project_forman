package fourman.project1.domain.post;

import fourman.project1.domain.board.Board;
import fourman.project1.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

//    @Mapping(source = "postRequestDto.userId", target = "user")
//    @Mapping(source = "postRequestDto.boardId", target = "board")
    Post postRequestDtoToPost(PostRequestDto postRequestDto);

//    @Mapping(source = "post.user" ,target = "userId" , ignore = true)
//    @Mapping(source = "post.board" ,target = "boardId" , ignore = true)
    PostResponseDto postToPostResponseDto(Post post);
}
