package com.sl.ms.ordermanagement.controller;

import brave.sampler.Sampler;
import com.sl.ms.ordermanagement.dto.OrderDto;
import com.sl.ms.ordermanagement.model.Orders;
import com.sl.ms.ordermanagement.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class OrderController {

    //autowired the OrderService class
    @Autowired
    OrderService orderService;
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
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
        String uuid = UUID.randomUUID().toString();
        LOG.info("UUID: "+ uuid +" Save order ");
        orderService.saveOrders(dto,orderid,uuid);
        return "Ordered items";
    }

}
