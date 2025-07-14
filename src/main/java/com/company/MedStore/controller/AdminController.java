package com.company.MedStore.controller;


import com.company.MedStore.dao.AdminDao;
import com.company.MedStore.dao.CustomerDao;
import com.company.MedStore.dao.MedicineDao;
import com.company.MedStore.dao.OrderDao;
import com.company.MedStore.entity.Admin;
import com.company.MedStore.entity.Customer;
import com.company.MedStore.entity.Medicine;
import com.company.MedStore.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AdminController {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private MedicineDao medicineDao;
    @Autowired
    private OrderDao orderDao;

    @PostMapping("/admins")
    public ResponseEntity<?> saveAdmin(@RequestBody Admin admin){
        Admin admin1 = adminDao.saveAdminDao(admin);
        Map<String, Admin> map = new HashMap<>();
        if (admin1 != null){
            map.put("data found", admin1);
            return ResponseEntity.status(HttpStatus.CREATED).body(map);
        }
        map.put("data not  found found", admin1);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @GetMapping("/getAdminByUsername/{userName}")
    public ResponseEntity<?> getAdminByUsername(@PathVariable String userName) {
        Admin admin = adminDao.findAdminByUserNameDao(userName);
        Map<String, Admin> map = new HashMap<>();
        if (admin != null){
            map.put("data found", admin);
            return ResponseEntity.status(HttpStatus.FOUND).body(map);
        }
        map.put("data not  found found", admin);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }


    @PostMapping("/customers")
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerDao.saveCustomerDao(customer);
        Map<String, Customer> map = new HashMap<>();
        if (savedCustomer != null) {
            map.put("data found", savedCustomer);
            return ResponseEntity.status(HttpStatus.CREATED).body(map);
        }
        map.put("data not found", savedCustomer);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @PostMapping("/medicines")
    public ResponseEntity<?> saveMedicine(@RequestBody Medicine medicine) {
        Medicine savedMedicine = medicineDao.saveMedicineDao(medicine);
        Map<String, Medicine> map = new HashMap<>();
        if (savedMedicine != null) {
            map.put("data found", savedMedicine);
            return ResponseEntity.status(HttpStatus.CREATED).body(map);
        }
        map.put("data not found", savedMedicine);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @PostMapping("/saveAllMedicine")
    public ResponseEntity<?> saveAllMedicine(@RequestBody List<Medicine> medicines) {
        List<Medicine> savedMedicine = medicineDao.saveAllMedicineDao(medicines);
        Map<String, Medicine> map = new HashMap<>();
        for (Medicine medicine : savedMedicine) {
            if (medicine != null) {
                map.put("data found", medicine);
                return ResponseEntity.status(HttpStatus.OK).body(map);
            }else {
                map.put("data not found", medicine);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
            }
        }
        return null;
    }

    @PostMapping("/orders")
    public ResponseEntity<?> saveOrder(@RequestBody Order order) {
        Order savedOrder = orderDao.createOrder(order);
        Map<String, Order> map = new HashMap<>();
        if (savedOrder != null) {
            map.put("data found", savedOrder);
            return ResponseEntity.status(HttpStatus.CREATED).body(map);
        }
        map.put("data not found", savedOrder);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<?> getAllOrders() {
        List<Order> orders= orderDao.getAllOrderDao();
        Map<String, Order> map = new HashMap<>();
        for (Order order : orders) {
            if (order != null) {
                map.put("data found", order);
                return ResponseEntity.status(HttpStatus.FOUND).body(map);
            }else {
                map.put("data not found", order);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
            }
        }
        return null;
    }

    @GetMapping("/getOrderById/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderDao.getOrderByIdDao(id);
        Map<String, Optional<Order>> map = new HashMap<>();
        if (order.isPresent()) {
            map.put("data found", order);
            return ResponseEntity.status(HttpStatus.FOUND).body(map);
        }
        map.put("data not found", order);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);

    }

    @DeleteMapping("/deleteOrderById/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderDao.deleteOrderByIdDao(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}



