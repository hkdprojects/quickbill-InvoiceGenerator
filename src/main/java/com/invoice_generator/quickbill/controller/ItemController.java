package com.invoice_generator.quickbill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.invoice_generator.quickbill.entity.Item;
import com.invoice_generator.quickbill.services.ItemService;

import java.util.List;

@Controller
@EnableMethodSecurity
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_RECEPTIONIST')")
    // View all items
    @GetMapping("/list")
    public String listItems(Model model) {
        List<Item> items = itemService.getAllItems();
        model.addAttribute("items", items);
        return "item-list"; // points to item-list.html
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    // Show add form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("item", new Item());
        return "item-form"; // points to item-form.html
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    // Save item (add or edit)
    @PostMapping("/save")
    public String saveItem(@ModelAttribute("item") Item item) {
        itemService.saveItem(item);
        return "redirect:/items/list";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    // Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Item item = itemService.getItemById(id);
        model.addAttribute("item", item);
        return "item-form";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    // Delete item
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        itemService.deleteItem(id);
        return "redirect:/items/list";
    }

}
