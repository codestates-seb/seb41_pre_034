package com.preproject.server.user.mapper;

import com.preproject.server.user.dto.UserPatchDto;
import com.preproject.server.user.dto.UserPostDto;
import com.preproject.server.user.dto.UserSimpleResponseDto;
import com.preproject.server.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User UserPostDtoToEntity(UserPostDto userPostDto);


    User UserPatchDtoToEntity(UserPatchDto userPatchDto);

    UserSimpleResponseDto userEntityToSimpleResponseDto(User user);


    List<UserSimpleResponseDto> UserListToResponseDtoList(List<User> userList);



}
