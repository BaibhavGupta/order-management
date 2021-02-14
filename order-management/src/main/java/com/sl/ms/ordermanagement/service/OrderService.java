package com.sl.ms.ordermanagement.service;

import brave.sampler.Sampler;
import com.sl.ms.ordermanagement.dto.OrderDto;
import com.sl.ms.ordermanagement.exception.ItemNotfound;
import com.sl.ms.ordermanagement.exception.OrderNotfound;
import com.sl.ms.ordermanagement.model.Items;
import com.sl.ms.ordermanagement.model.Orders;
import com.sl.ms.ordermanagement.repository.OrderRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
    //getting all orders
    public List<Orders> getAllOrders() {
        List<Orders> ordersList = new ArrayList<Orders>();
        orderRepository.findAll().forEach(orders -> ordersList.add(orders));
        return ordersList;
    }

    //getting a specific record
    public Orders getOrderById(int id) {
        LOG.info("Check Order By Id ");
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

    public void saveOrders(OrderDto dto, int orderid, String uuid) {
        Orders orders = new Orders();
        Items items = new Items();
        List<Items> list = new ArrayList<>();
        LOG.info("UUID: "+ uuid +" Check Item in Inventory. ");
        Object object = serviceCall.callInventoryMgmt(orderid,uuid);
        if (object instanceof Exception){
            LOG.info("UUID: "+ uuid +" Item not found in inventory. ");
            throw new ItemNotfound();
        }else if(object instanceof String){
            LOG.info("UUID: "+ uuid + " " + object.toString() );
        }else if (object instanceof JSONObject) {
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
                LOG.info("UUID: "+ uuid +" Item found in inventory and order saved. ");
                orderRepository.save(orders);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}