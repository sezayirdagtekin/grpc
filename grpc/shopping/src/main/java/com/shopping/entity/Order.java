package com.shopping.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Order {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int orderId;
    int userId;
    int numberOfItems;
    double totalAmount;
    
}
