package com.walmartapi.entity;

import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;

    @ManyToOne@JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToOne(cascade = CascadeType.ALL)@JoinColumn(name = "inventory_id", unique = true)
    private ProductInventoryEntity inventory;

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }
}
