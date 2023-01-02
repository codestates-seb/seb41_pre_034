package com.preproject.server.user.service;

import com.preproject.server.constant.ErrorCode;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    @DisplayName("User Service 검증 로직 TEST")
    void verifyLogic() {
        // Given
        User testUser = createTestUser(1L);
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(testUser));
        given(userRepository.findCustomById(anyLong())).willReturn(null);
        // When
        Throwable throwableByCreate = Assertions.catchThrowable(() -> userService.createUser(testUser));
        Throwable throwableByFind = Assertions.catchThrowable(() -> userService.findUser(testUser.getUserId()));
        Throwable throwableByDelete = Assertions.catchThrowable(() -> userService.deleteUser(testUser.getUserId()));

        // Then
        assertThat(throwableByCreate)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.USER_EMAIL_EXISTS.getMessage());
        assertThat(throwableByFind)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.USER_NOT_FOUND.getMessage());
        assertThat(throwableByDelete)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("User Service UpdateUser TEST")
    void updateUser() {
        // Given
        User testUser = createTestUser(1L);
        User patchUser = createPatchUser(1L);
        given(userRepository.findCustomById(anyLong())).willReturn(testUser);
        // When
        User user = userService.updateUser(patchUser);
        // Then
        assertThat(user.getDisplayName()).isEqualTo(patchUser.getDisplayName());
        assertThat(user.getPassword()).isEqualTo(patchUser.getPassword());
    }

    private User createTestUser(Long userId) {
        User testUser = new User(
                "test@test.com",
                "1111!",
                "testUser",
                true);
        testUser.setUserId(userId);
        return testUser;
    }
    private User createPatchUser(Long userId) {
        User testUser = new User(
                "patch@test.com",
                "2222!",
                "patchUser",
                true);
        testUser.setUserId(userId);
        return testUser;
    }

}