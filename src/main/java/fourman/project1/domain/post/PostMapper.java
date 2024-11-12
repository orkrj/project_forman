package fourman.project1.domain.post;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "postRequestDto.userId", target = "user", ignore = true)
    @Mapping(source = "postRequestDto.boardId", target = "board", ignore = true)
    Post postRequestDtoToPost(PostRequestDto postRequestDto);

//    @Mapping(source = "post.user" ,target = "userId" , ignore = true)
//    @Mapping(source = "post.board" ,target = "boardId" , ignore = true)
    PostResponseDto postToPostResponseDto(Post post);
}
