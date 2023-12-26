package app.mapper;

import app.model.dto.request.UserRequestDto;
import app.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id" ,ignore = true),
            @Mapping(target = "createdAt" ,ignore = true),
            @Mapping(target = "updatedAt" ,ignore = true),
            @Mapping(target = "userSituation" ,ignore = true),
            @Mapping(target = "userDetails" ,ignore = true),
            @Mapping(target = "token" ,ignore = true),
            @Mapping(target = "posts" ,ignore = true),
    })
    User map(UserRequestDto requestDto);
}
