package com.parrino.riccardo.model;

public class Order {
    private Long id;
    private String name;
    private Double price;

    public Long getId() {
        return this.id;
    }

    public Order setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Order setName(String name) {
        this.name = name;
        return this;
    }

    public Double getPrice() {
        return this.price;
    }

    public Order setPrice(Double price) {
        this.price = price;
        return this;
    }

}
