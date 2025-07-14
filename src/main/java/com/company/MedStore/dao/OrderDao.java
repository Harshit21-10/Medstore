package com.company.MedStore.dao;

import com.company.MedStore.entity.Medicine;
import com.company.MedStore.entity.Order;
import com.company.MedStore.entity.OrderItem;
import com.company.MedStore.repository.MedicineRepository;
import com.company.MedStore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrderDao {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MedicineRepository medicineRepository;

    public List<Order> getAllOrderDao(){
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderByIdDao(Long id){
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order){
        if (order.getCustomer()==null){
            throw new IllegalArgumentException("customer is mandatory");
        }
        if (order.getOrderItems() == null){
            throw new IllegalArgumentException("Atleast one item is mandatory");
        }

        double calculateSubTotal = 0;
        for (OrderItem item : order.getOrderItems()){
            if (item.getMedicine() == null){
                throw new IllegalArgumentException("Medicine is mandatory");
            }
            Medicine medicine = medicineRepository.findById(item.getMedicine().getId()).orElseThrow(() ->
                    new IllegalArgumentException("Medicine with id " +item.getMedicine().getId()+" not found"));
            if (medicine.getQuantityInStock()< item.getQuantity()){
                throw new IllegalArgumentException("Insufficient stock for medicine "+medicine.getName());
            }
            item.setOrder(order);
            item.setSubtotal(item.getQuantity()*medicine.getPrice());
            calculateSubTotal += item.getSubtotal();
        }
        if (Math.abs(order.getTotalAmount() - calculateSubTotal) > 0.01){
            throw new IllegalArgumentException("Total amount does not match sum of order items");
        }
        return orderRepository.save(order);
    }

    public void deleteOrderByIdDao(Long id){
        if (!orderRepository.existsById(id)){
            throw new IllegalArgumentException("id doesnot exist");
        }
        orderRepository.deleteById(id);

    }

}
