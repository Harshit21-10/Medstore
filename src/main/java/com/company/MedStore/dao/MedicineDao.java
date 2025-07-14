package com.company.MedStore.dao;

import com.company.MedStore.entity.Medicine;
import com.company.MedStore.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class MedicineDao {
    @Autowired
    private MedicineRepository medicineRepository;

    public Medicine saveMedicineDao(Medicine medicine){
        if (medicine.getExpiryDate().isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Expiry date must be future date");
        }
        return medicineRepository.save(medicine);
    }

    public List<Medicine> saveAllMedicineDao(List<Medicine> medicines){
        for (Medicine medicine : medicines){
            if (medicine.getExpiryDate().isBefore(LocalDate.now())){
                throw new IllegalArgumentException("Expiry date must be future date");
            }
        }
        return medicineRepository.saveAll(medicines);
    }
}
