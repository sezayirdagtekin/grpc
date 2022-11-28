package com.shopping.service;

import java.util.List;
import java.util.logging.Logger;

import com.shopping.dao.OrderDao;
import com.shopping.entity.Order;
import com.shopping.stub.order.OrderRequest;
import com.shopping.stub.order.OrderResponse;
import com.shopping.stub.order.OrderServiceGrpc.OrderServiceImplBase;

import io.grpc.stub.StreamObserver;

public class OrderServiceImpl extends OrderServiceImplBase {
	
	private Logger logger = Logger.getLogger(OrderServiceImpl.class.getName());
	private OrderDao orderDao = new OrderDao();

	@Override
	public void getOrdersForUser(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {

		List<Order> orders = orderDao.getOrdersForUsers(request.getUserId());
		  
		logger.info("Got orders from OrderDao and converting to OrderResponse proto objects");
		   
		List<com.shopping.stub.order.Order> protoOder = orders.stream()
				.map(o -> com.shopping.stub.order.Order.newBuilder().setUserId(o.getUserId()).setOrderId(o.getOrderId())
						.setNoOfItems(o.getNumberOfItems()).setTotalAmount(o.getTotalAmount()).build())
				.toList();

		OrderResponse orderResponse = OrderResponse.newBuilder().addAllOrder(protoOder).build();

		responseObserver.onNext(orderResponse);
		responseObserver.onCompleted();
	}

}
