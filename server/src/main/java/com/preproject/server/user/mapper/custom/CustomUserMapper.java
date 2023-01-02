package com.preproject.server.user.mapper.custom;

import com.preproject.server.user.dto.UserResponseDto;
import com.preproject.server.user.entity.User;

public interface CustomUserMapper {

    UserResponseDto userEntityToResponseDto(User user);

}
