package com.invoice_generator.quickbill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.invoice_generator.quickbill.entity.Item;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // Find by name
    List<Item> findAllByItemName(String itemName);

    // Find by price less than or equal
    @Query("FROM Item i WHERE i.itemUnitPrice <= :itemUnitPrice")
    List<Item> findAllByUnitPriceLessThanEqual(@Param("itemUnitPrice") double itemUnitPrice);

    // Find by name AND price
    @Query("FROM Item i WHERE i.itemName = :itemName AND i.itemUnitPrice <= :itemUnitPrice")
    List<Item> findByNameAndPriceLessThanEqual(
            @Param("itemName") String itemName,
            @Param("itemUnitPrice") double itemUnitPrice);

    //Find by quantity
    List<Item> findAllByItemQuantity(int itemQuantity);
}
