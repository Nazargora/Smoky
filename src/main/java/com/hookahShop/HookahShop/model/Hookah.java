package com.hookahShop.HookahShop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hookah {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "model")
    private String model;

    @Column(name = "color")
    private String color;

    @Column(name = "price")
    private double price;

    @Column(name = "count")
    private int count;

    @ManyToMany
    @JoinTable(name = "order_hookahs",
            joinColumns = {@JoinColumn(name = "hookah_id")},
            inverseJoinColumns = {@JoinColumn(name = "order_id")})
    private List<Order> orders;


}
