package com.clouway.http;

import com.clouway.core.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Post;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
@At("/registration")
@Service
@Singleton
public class RegistrationController {

    private UserRepository userRepository;
    private UserValidator validator;

    @Inject
    public RegistrationController(UserRepository userRepository, UserValidator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @Post
    public Reply<?> register(Request request) {

        User user = request.read(User.class).as(Json.class);

        if (validator.userIsCorrect(user)) {

            if (!userRepository.exists(user.getUserName())) {
                userRepository.register(user);
                return Reply.saying().ok();
            }
        }
        return Reply.saying().error();
    }
}