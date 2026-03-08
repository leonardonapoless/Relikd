package com.leocodes.relikd.service;

import com.leocodes.relikd.model.Computer;
import com.leocodes.relikd.model.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartService {

    private static CartService instance;
    private final List<OrderItem> items = new ArrayList<>();
    private final List<Computer> computerReferences = new ArrayList<>();

    private CartService() {
    }

    public static CartService get() {
        if (instance == null) {
            instance = new CartService();
        }
        return instance;
    }

    public void addItem(Computer computer, int quantity) {
        var existing = getExistingItem(computer.getId());
        if (existing.isPresent()) {
            var item = existing.get();
            items.remove(item);
            items.add(new OrderItem(0, 0, computer.getId(), item.getQuantity() + quantity, computer.getPrice()));
        } else {
            items.add(new OrderItem(0, 0, computer.getId(), quantity, computer.getPrice()));
            computerReferences.add(computer);
        }
    }

    public void removeItem(int computerId) {
        items.removeIf(i -> i.getComputerId() == computerId);
        computerReferences.removeIf(c -> c.getId() == computerId);
    }

    public void clear() {
        items.clear();
        computerReferences.clear();
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public List<Computer> getComputerReferences() {
        return new ArrayList<>(computerReferences);
    }

    public double getTotal() {
        return items.stream()
                .mapToDouble(i -> i.getPriceAtPurchase() * i.getQuantity())
                .sum();
    }

    private Optional<OrderItem> getExistingItem(int computerId) {
        return items.stream().filter(i -> i.getComputerId() == computerId).findFirst();
    }
}
