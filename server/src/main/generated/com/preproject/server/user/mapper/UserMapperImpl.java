package com.preproject.server.user.mapper;

import com.preproject.server.user.dto.UserPatchDto;
import com.preproject.server.user.dto.UserPostDto;
import com.preproject.server.user.dto.UserSimpleResponseDto;
import com.preproject.server.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-29T16:08:40+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.16 (Azul Systems, Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User UserPostDtoToEntity(UserPostDto userPostDto) {
        if ( userPostDto == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( userPostDto.getEmail() );
        user.setPassword( userPostDto.getPassword() );
        user.setDisplayName( userPostDto.getDisplayName() );
        user.setEmailNotice( userPostDto.getEmailNotice() );

        return user;
    }

    @Override
    public User UserPatchDtoToEntity(UserPatchDto userPatchDto) {
        if ( userPatchDto == null ) {
            return null;
        }

        User user = new User();

        user.setUserId( userPatchDto.getUserId() );
        user.setPassword( userPatchDto.getPassword() );
        user.setDisplayName( userPatchDto.getDisplayName() );

        return user;
    }

    @Override
    public UserSimpleResponseDto userEntityToSimpleResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserSimpleResponseDto userSimpleResponseDto = new UserSimpleResponseDto();

        userSimpleResponseDto.setUserId( user.getUserId() );
        userSimpleResponseDto.setEmail( user.getEmail() );
        userSimpleResponseDto.setDisplayName( user.getDisplayName() );
        userSimpleResponseDto.setEmailNotice( user.getEmailNotice() );
        if ( user.getUserStatus() != null ) {
            userSimpleResponseDto.setUserStatus( user.getUserStatus().name() );
        }
        if ( user.getLoginType() != null ) {
            userSimpleResponseDto.setLoginType( user.getLoginType().name() );
        }
        userSimpleResponseDto.setCreateAt( user.getCreateAt() );
        userSimpleResponseDto.setUpdateAt( user.getUpdateAt() );

        return userSimpleResponseDto;
    }

    @Override
    public List<UserSimpleResponseDto> UserListToResponseDtoList(List<User> userList) {
        if ( userList == null ) {
            return null;
        }

        List<UserSimpleResponseDto> list = new ArrayList<UserSimpleResponseDto>( userList.size() );
        for ( User user : userList ) {
            list.add( userEntityToSimpleResponseDto( user ) );
        }

        return list;
    }
}
