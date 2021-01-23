package com.sl.ms.ordermanagement.service;

import com.sl.ms.ordermanagement.dto.OrderDto;
import com.sl.ms.ordermanagement.exception.ItemNotfound;
import com.sl.ms.ordermanagement.exception.OrderNotfound;
import com.sl.ms.ordermanagement.model.Items;
import com.sl.ms.ordermanagement.model.Orders;
import com.sl.ms.ordermanagement.repository.OrderRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ServiceCall serviceCall;

    //getting all orders
    public List<Orders> getAllOrders() {
        List<Orders> ordersList = new ArrayList<Orders>();
        orderRepository.findAll().forEach(orders -> ordersList.add(orders));
        return ordersList;
    }

    //getting a specific record
    public Orders getOrderById(int id) {
        Optional<Orders> orders =  orderRepository.findById(id);

        if (orders.isPresent())
            return orders.get();
        else
            throw new OrderNotfound();
    }

    //save order
    //deleting a specific record
    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }

    public void saveOrders(OrderDto dto, int orderid) {
        Orders orders = new Orders();
        Items items = new Items();
        List<Items> list = new ArrayList<>();
        Object object = serviceCall.callInventoryMgmt(orderid);
        if (object instanceof Exception)
            throw new ItemNotfound();
        else if (object instanceof JSONObject) {
            try {
                JSONObject json = (JSONObject) object;
                items.setId(json.getInt("id"));
                items.setName(json.getString("name"));
                items.setAmount(dto.getTotalAmount());
                items.setPrice(json.getDouble("price"));
                items.setQuantity(10);

                orders.setId(orderid);
                orders.setName(dto.getName());
                orders.setTotalAmount(dto.getTotalAmount());
                list.add(items);
                orders.setItems(list);

                orderRepository.save(orders);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}