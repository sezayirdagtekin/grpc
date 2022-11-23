package com.shopping.service;

import com.shopping.dao.UserDao;
import com.shopping.entity.User;
import com.shopping.stub.user.Gender;
import com.shopping.stub.user.UserRequest;
import com.shopping.stub.user.UserResponse;
import com.shopping.stub.user.UserServiceGrpc.UserServiceImplBase;

import io.grpc.stub.StreamObserver;

public class UserServiceImpl extends UserServiceImplBase {

	@Override
	public void getUserDetails(UserRequest request, StreamObserver<UserResponse> responseObserver) {

		UserDao userDao = new UserDao();

		User user = userDao.getUserDetail(request.getUsername());

		UserResponse userResponse = UserResponse.newBuilder()
		.setName(user.getName())
		.setUsername(user.getUsername())
		.setId(user.getId())
	    .setGender(Gender.valueOf(user.getGender()))
	    .build();
		
		responseObserver.onNext(userResponse); 
		responseObserver.onCompleted(); //Ensure rpc call complete
		
		

	}

}
