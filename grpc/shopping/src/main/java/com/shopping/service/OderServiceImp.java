package com.shopping.service;

import java.util.List;

import com.shopping.dao.OrderDao;
import com.shopping.entity.Order;
import com.shopping.stub.order.OrderRequest;
import com.shopping.stub.order.OrderResponse;
import com.shopping.stub.order.OrderServiceGrpc.OrderServiceImplBase;

import io.grpc.stub.StreamObserver;

public class OderServiceImp extends OrderServiceImplBase {

	private OrderDao orderDao = new OrderDao();

	@Override
	public void getOrdersForUser(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {

		List<Order> orders = orderDao.getOrdersForUsers(request.getUserId());
		List<com.shopping.stub.order.Order> protoOder = orders.stream()
				.map(o -> com.shopping.stub.order.Order.newBuilder().setUserId(o.getUserId()).setOrderId(o.getOrderId())
						.setNoOfItems(o.getNumberOfItems()).setTotalAmount(o.getTotalAmount()).build())
				.toList();

		OrderResponse orderResponse = OrderResponse.newBuilder().addAllOrder(protoOder).build();

		responseObserver.onNext(orderResponse);
		responseObserver.onCompleted();
	}

}
