package com.tedu.sp04.order.service;

import org.springframework.stereotype.Service;

import com.tedu.sp01.pojo.Order;
import com.tedu.sp01.service.OrderService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	@Override
	public Order getOrder(String orderId) {
		//TODO: 调用商品微服务,获取订单中的商品列表数据
		//TODO: 调用用户微服务,获取用户数据
		Order order = new Order();
		order.setId(orderId);
		//order.setUser(user);
		//order.setItems(items);
		return order;
	}

	@Override
	public void addOrder(Order order) {
		//TODO: 减少商品的库存
		//TODO: 增加用户的积分
		log.info("保存订单: "+order);
	}

}




