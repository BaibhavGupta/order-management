package com.sl.ms.ordermanagement.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Orders {

    @Id
    @Column
    private int id;
    @Column
    private String name;
    @Column
    private Double totalAmount;

    @OneToMany(targetEntity = Items.class,cascade = CascadeType.ALL)
    @JoinColumn(name="order_id")
    private List<Items> items;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }


}
