package com.shopping.order;

import java.util.List;

import com.shopping.stub.order.Order;
import com.shopping.stub.order.OrderRequest;
import com.shopping.stub.order.OrderResponse;
import com.shopping.stub.order.OrderServiceGrpc;

import io.grpc.Channel;

public class OrderClent {

	private OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub;

	public OrderClent(Channel channel) {
		orderServiceBlockingStub = OrderServiceGrpc.newBlockingStub(channel);
	}

	public List<Order> getOrders(int userId) {

		OrderRequest request = OrderRequest.newBuilder().setUserId(userId).build();

		OrderResponse orderResponse = orderServiceBlockingStub.getOrdersForUser(request);

		return orderResponse.getOrderList();

	}

}
