package com.invoice_generator.quickbill.entity;
import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long itemId;

    @NotBlank(message = "itemName is required")
    private String itemName;

    @NotBlank(message = "itemDescription is required")
    private String itemDescription;

    private int itemQuantity;

    private BigDecimal  itemUnitPrice;

    // Getters and setters
    public Long getItemId() {
        return itemId;
    }
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getItemDescription() {
        return itemDescription;
    }
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    public int getItemQuantity() {
        return itemQuantity;
    }
    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
    public BigDecimal  getItemUnitPrice() {
        return itemUnitPrice;
    }
    public void setItemUnitPrice(BigDecimal  itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    //to String
    @Override
    public String toString() {
        return "Item [itemId=" + itemId + ", itemName=" + itemName + ", itemDescription=" + itemDescription
                + ", itemQuantity=" + itemQuantity + ", itemUnitPrice=" + itemUnitPrice + "]";
    }     
        
}

