package org.example.entitites;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.VENDING_MACHINE_STATE;

import java.util.HashMap;

@Getter
@Setter
public class VendingMachine {
    private VENDING_MACHINE_STATE currentVendingMachineState;
    private HashMap<Integer, ProductDetail> productList; // <product_id , productInfo>
}
