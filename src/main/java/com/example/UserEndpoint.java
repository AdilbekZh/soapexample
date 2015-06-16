package com.example;

import com.example.ws.AddUserRequest;
import com.example.ws.AddUserResponse;
import com.example.ws.GetUsersResponse;
import com.example.ws.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

/**
 * Created by HP on 14.06.2015.
 */

@Endpoint
public class UserEndpoint {

    private final static Logger logger = LoggerFactory.getLogger(UserEndpoint.class);

    private static final String NAMESPACE_URI = "http://example.com/ws";

    @Autowired
    private UserRepository userRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addUserRequest")
    @ResponsePayload
    public AddUserResponse addUser(@RequestPayload AddUserRequest request) {
        AddUserResponse response = new AddUserResponse();
        List<User> users = request.getUsers();
        logger.info("Request, users = {}", users);
        for (User u : users) {
            userRepository.addUser(u);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUsersResponse")
    @ResponsePayload
    public GetUsersResponse getUsers() {
        GetUsersResponse response = new GetUsersResponse();
        response.getUser().addAll(userRepository.getUserList());
        return response;
    }
}
