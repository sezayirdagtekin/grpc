package com.shopping.order.client;

import java.util.List;
import java.util.logging.Logger;

import com.shopping.stub.order.Order;
import com.shopping.stub.order.OrderRequest;
import com.shopping.stub.order.OrderResponse;
import com.shopping.stub.order.OrderServiceGrpc;

import io.grpc.Channel;

public class OrderClient {

    private Logger logger = Logger.getLogger(OrderClient.class.getName());
    // get a stub object
    // call service method
    
	private OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub;

	public OrderClient(Channel channel) {
		orderServiceBlockingStub = OrderServiceGrpc.newBlockingStub(channel);
	}

	public List<Order> getOrders(int userId) {
		
		logger.info("OrderClient calling the OrderService method");
		 
		OrderRequest request = OrderRequest.newBuilder().setUserId(userId).build();

		OrderResponse orderResponse = orderServiceBlockingStub.getOrdersForUser(request);

		return orderResponse.getOrderList();

	}

}
