package com.tdtu.midterm.service.Imp;

import com.tdtu.midterm.model.CartItem;
import com.tdtu.midterm.model.Order;
import com.tdtu.midterm.model.OrderDetail;
import com.tdtu.midterm.repository.OrderDetailRepository;
import com.tdtu.midterm.repository.OrderRepository;
import com.tdtu.midterm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public Order createOrder(List<CartItem> cartItems, String customerName, String customerPhone, String customerAddress) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setCustomerPhone(customerPhone);
        order.setCustomerAddress(customerAddress);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderDetail orderDetail = mapCartItemToOrderItem(cartItem, order);
            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);
        orderRepository.save(order);
        orderDetailRepository.saveAll(orderDetails);
        return order;
    }

    private OrderDetail mapCartItemToOrderItem(CartItem cartItem, Order order) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setProduct(cartItem.getProduct());
        orderDetail.setQuantity(cartItem.getQuantity());
        return orderDetail;
    }
}
