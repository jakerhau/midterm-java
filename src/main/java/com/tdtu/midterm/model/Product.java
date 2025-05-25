package com.tdtu.midterm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tdtu.midterm.constant.Brand;
import com.tdtu.midterm.constant.Color;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "products")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Brand brand;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Color color;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_category"))
    @JsonBackReference
    private Category category;
}
