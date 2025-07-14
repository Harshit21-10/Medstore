package com.company.MedStore.dao;

import com.company.MedStore.entity.Admin;
import com.company.MedStore.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminDao {
    @Autowired
    private AdminRepository adminRepository;

    public Admin saveAdminDao(Admin admin){
        if (adminRepository.findByUserName(admin.getUserName()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        return adminRepository.save(admin);
    }

    public Admin findAdminByUserNameDao(String userName){

        return adminRepository.findByUserName(userName);
    }

}
