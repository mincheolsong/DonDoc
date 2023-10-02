package com.dondoc.backend.user.service;

import com.dondoc.backend.user.dto.user.*;
import com.dondoc.backend.user.entity.User;

public interface UserService {

    SignUpDto.Response signUp(SignUpDto.Request signUpDto) throws Exception;

    SignInDto.Response signIn(SignInDto.Request req) throws Exception;

    CertificationDto.Response sendSMS(String phoneNumber);

    FindPasswordDto.Response findPassword(FindPasswordDto.Request req);

    ProfileDto.Response findProfile(Long userId);

    ProfileDto.Response myProfile(Long userId);

    FindUserDto.Response findUser(String phoneNumber, String username);

    UpdateUserDto.Response updateUser(UpdateUserDto.Request req, Long id);

    User findById(Long userId);

    void logOut(String token);

    IntroduceDto.Response changeIntroduce(Long userId, String introduce);
}
