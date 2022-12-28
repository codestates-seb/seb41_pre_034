package com.preproject.server.user.service;


import com.preproject.server.constant.ErrorCode;
import com.preproject.server.constant.LoginType;
import com.preproject.server.constant.UserStatus;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.repository.UserRepository;
import com.preproject.server.utils.JwtAuthorityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtAuthorityUtils authorityUtils;

    /* 사용자 생성 */
    public User createUser(User user) {
        user.setUserStatus(UserStatus.ACTIVITY);
        user.setLoginType(LoginType.BASIC);
        String email = user.getEmail();
        verifyUserByEmail(email);

        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);

        List<String> roles = authorityUtils.createRoles(email);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    /* 사용자 정보 업데이트 */
    public User updateUser(User user) {
        return checkUserField(user);
    }

    /* 사용자 단건 조회 */
    public User findUser(Long userId) {

        return verifiedUserById(userId);
    }

    /* 사용자 전체 조회 페이징 */
    public Page<User> findUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void deleteUsers() {
        userRepository.deleteAll();
    }

    public void deleteUser(Long userId) {
        User user = verifiedUserById(userId);
        userRepository.delete(user);
    }


    /* 검증 로직 */
    private User checkUserField(User user) {
        User findUser = verifiedUserById(user.getUserId());
        Optional.ofNullable(user.getDisplayName())
                .ifPresent(findUser::setDisplayName);
        Optional.ofNullable(user.getPassword())
                .ifPresent(findUser::setPassword);
        return findUser;
    }

    public void verifyUserByEmail(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isPresent()) {
            throw new ServiceLogicException(ErrorCode.USER_EXISTS);
        }
    }

    public User verifiedUserById(Long userId) {
        Optional<User> findUser =
                Optional.ofNullable(userRepository.findCustomById(userId));
        return findUser.orElseThrow(
                () -> new ServiceLogicException(ErrorCode.USER_NOT_FOUND)
        );
    }
}
