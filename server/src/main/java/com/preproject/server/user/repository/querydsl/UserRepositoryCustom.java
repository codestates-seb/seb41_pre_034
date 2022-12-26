package com.preproject.server.user.repository.querydsl;

import com.preproject.server.user.entity.User;

public interface UserRepositoryCustom {

    User findCustomById(Long userId);
}
