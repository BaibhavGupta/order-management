package com.sl.ms.ordermanagement.controller;

import com.sl.ms.ordermanagement.dto.OrderDto;
import com.sl.ms.ordermanagement.model.Orders;
import com.sl.ms.ordermanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class OrderController {

    //autowired the OrderService class
    @Autowired
    OrderService orderService;
    //creating a get mapping that retrieves all the orders detail from the database
    @GetMapping("/orders")
    private List<Orders> getAllOrders()
    {
        return orderService.getAllOrders();
    }
    //creating a get mapping that retrieves the detail of a specific orders
    @GetMapping("/orders/{id}")
    private  Orders getOrderById(@PathVariable("id") int id){
        return  orderService.getOrderById(id);
    }

    //creating a delete mapping that deletes a specific orders
    @DeleteMapping("/orders/{id}")
    private void deleteOrders(@PathVariable("id") int id)
    {
        orderService.deleteOrder(id);
    }
    //creating post mapping that post the orders detail in the database
    @PostMapping("/orders/{order_id}")
    private String saveOrders(@RequestBody OrderDto dto, @PathVariable(name = "order_id") int orderid)
    {
        orderService.saveOrders(dto,orderid);
        return "Ordered items";
    }

}
