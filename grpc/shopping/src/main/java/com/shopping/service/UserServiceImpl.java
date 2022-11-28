package com.shopping.service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.shopping.dao.UserDao;
import com.shopping.entity.User;
import com.shopping.order.client.OrderClient;
import com.shopping.server.OrderServer;
import com.shopping.stub.order.Order;
import com.shopping.stub.user.Gender;
import com.shopping.stub.user.UserRequest;
import com.shopping.stub.user.UserResponse;
import com.shopping.stub.user.UserServiceGrpc.UserServiceImplBase;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class UserServiceImpl extends UserServiceImplBase {
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
			
	private UserDao userDao = new UserDao();
	
	@Override
	public void getUserDetails(UserRequest request, StreamObserver<UserResponse> responseObserver) {

		User user = userDao.getUserDetail(request.getUsername());

		UserResponse userResponse = UserResponse.newBuilder()
		.setName(user.getName())
		.setUsername(user.getUsername())
		.setId(user.getId())
	    .setGender(Gender.valueOf(user.getGender()))
	    .build();

		//getOrders(userResponse);
		
		responseObserver.onNext(userResponse); 
		responseObserver.onCompleted(); //Ensure rpc call complete
		
		

	}

	private void getOrders(UserResponse userResponse) {
		ManagedChannel channel= ManagedChannelBuilder
								.forTarget("localhost:5002")
								.usePlaintext().build();
		
		OrderClient orderClient = new OrderClient(channel);
		List<Order> order = orderClient.getOrders(userResponse.getId());
		
		//If you do not  have multiple call shut down channel. Otherwise keep channel open
		
		try {
			channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, "Channel did not shutdown", e);
		}
	}

}
