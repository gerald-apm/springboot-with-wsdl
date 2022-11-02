package com.geraldapm.xmltesting.endpoint;

import com.geraldapm.xmltesting.service.UserService;
import id.my.gpm.service.xmltesting.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class UserEndpoint {
    private static final String NAMESPACE_URL="http://service.gpm.my.id/xmltesting";

    @Autowired
    private UserService userService;

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUserByName(@RequestPayload GetUserRequest request) throws Exception{
        GetUserResponse userResponse = new GetUserResponse();
        userResponse.setUser(userService.getUserByName(request.getName()));
        return userResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getAllUserRequest")
    @ResponsePayload
    public GetAllUserResponse getAllUsers() throws Exception{
        GetAllUserResponse userResponse = new GetAllUserResponse();
        userResponse.getUser().addAll(userService.getAllUsers());
        return userResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "addUserRequest")
    @ResponsePayload
    public AddUserResponse addNewUser(@RequestPayload AddUserRequest request) throws Exception{
        AddUserResponse userResponse = new AddUserResponse();
        userResponse.setUser(userService.addNewUser(request.getUser()));
        return userResponse;
    }

}
