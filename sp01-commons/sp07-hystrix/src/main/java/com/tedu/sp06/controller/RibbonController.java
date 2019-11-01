package com.tedu.sp06.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tedu.sp01.pojo.Item;
import com.tedu.sp01.pojo.Order;
import com.tedu.sp01.pojo.User;
import com.tedu.web.util.JsonResult;

@RestController
public class RibbonController {
	@Autowired
	private RestTemplate rt;
	
	@GetMapping("/item-service/{orderId}")
	@HystrixCommand(fallbackMethod = "getItemsFB")
	public JsonResult<List<Item>> getItems(@PathVariable String orderId){
		try {
			return rt.getForObject("http://item-service/{1}", JsonResult.class, orderId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	} 
	@PostMapping("/item-service/decreaseNumber")
	@HystrixCommand(fallbackMethod = "decreaseNumberFB")
	public JsonResult decreaseNumber(@RequestBody List<Item> items) {
		return rt.postForObject("http://item-service/decreaseNumber", items, JsonResult.class);
	}
	
	@GetMapping("/user-service/{userId}")
	@HystrixCommand(fallbackMethod = "getUserFB")
	public JsonResult<User> getUser(@PathVariable Integer userId) {
		return rt.getForObject("http://user-service/{1}", JsonResult.class, userId);
	}

	@GetMapping("/user-service/{userId}/score")
	@HystrixCommand(fallbackMethod = "addScoreFB")
	public JsonResult addScore(
			@PathVariable Integer userId, Integer score) {
		return rt.getForObject("http://user-service/{1}/score?score={2}", JsonResult.class, userId, score);
	}
	
	@GetMapping("/order-service/{orderId}")
	@HystrixCommand(fallbackMethod = "getOrderFB")
	public JsonResult<Order> getOrder(@PathVariable String orderId) {
		return rt.getForObject("http://order-service/{1}", JsonResult.class, orderId);
	}
	
	@GetMapping("/order-service/")
	@HystrixCommand(fallbackMethod = "addOrderFB")
	public JsonResult addOrder() {
		return rt.getForObject("http://order-service/", JsonResult.class);
	}
	
	//////////////////////////////////////
	public JsonResult<List<Item>> getItemsFB(String orderId){
		return JsonResult.err("获取订单的商品列表失败");
	} 
	public JsonResult decreaseNumberFB(List<Item> items) {
		return JsonResult.err("减少商品库存失败");
	}
	
	public JsonResult<User> getUserFB(Integer userId) {
		return JsonResult.err("获取用户信息失败");
	}

	public JsonResult addScoreFB(
			Integer userId, Integer score) {
		return JsonResult.err("增加用户积分失败");
	}
	
	public JsonResult<Order> getOrderFB(String orderId) {
		return JsonResult.err("获取订单失败");
	}
	
	public JsonResult addOrderFB() {
		return JsonResult.err("保存订单失败");
	}
}






