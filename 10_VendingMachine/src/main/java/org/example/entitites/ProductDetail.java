package org.example.entitites;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductDetail {
    private int productID;
    private String productName;
    private int cost;
    private int currentQty;
}
