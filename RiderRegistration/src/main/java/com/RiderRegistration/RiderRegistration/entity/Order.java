package com.RiderRegistration.RiderRegistration.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    private int id;
    private String dishName;
    private int itemCount;
    private String description;
    private String address;
}