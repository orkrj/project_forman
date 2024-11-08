package fourman.project1.domain.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "deletedAt", ignore = true)
    User toEntity(UserSignUpRequestDto userSignUpRequestDto);

//    UserSignUpRequestDto toDto(User user);
//
//    CustomUserDetails toCustomUserDetails(User user);
//    User toEntity(CustomUserDetails customUserDetails);

}
