package com.tdtu.midterm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "orders")
@Getter
@Setter
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<OrderDetail> orderDetails;

    private LocalDate orderDate = LocalDate.now();

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String customerPhone;

    @Column(nullable = false)
    private String customerAddress;

    @Transient
    public double getTotalOrderPrice() {
        double sum = 0D;
        List<OrderDetail> orderDetails = getOrderDetails();
        for (OrderDetail i : orderDetails) {
            sum += i.getTotalPrice();
        }
        return sum;
    }
}
