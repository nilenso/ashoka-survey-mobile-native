package com.infinitisuite.surveymobile.services;

import com.infinitisuite.surveymobile.handlers.UserLoginHandler;
import com.infinitisuite.surveymobile.models.User;

public interface IUserService {
    void login(User user, UserLoginHandler userLoginHandler);
}
