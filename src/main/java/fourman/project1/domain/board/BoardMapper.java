package fourman.project1.domain.board;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    Board boardRequestDtoToBoard(BoardRequestDto boardRequestDto);
    BoardResponseDto boardToBoardResponseDto(Board board);
}
