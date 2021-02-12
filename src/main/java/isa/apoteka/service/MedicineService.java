package isa.apoteka.service;

import java.util.List;

import isa.apoteka.domain.DrugForm;
import isa.apoteka.domain.DrugType;
import isa.apoteka.domain.Medicine;
import isa.apoteka.dto.FilteredMedicineDTO;
import isa.apoteka.dto.MedicineCreateDTO;
import isa.apoteka.dto.MedicineDTO;
import isa.apoteka.dto.MedicineNameDTO;
import isa.apoteka.dto.SearchFilterMedicineDTO;

public interface MedicineService {
	Medicine findOne(Long id);
	List<Medicine> findAll();
	List<Medicine> searchMedicinesByName(String name);
	Boolean isPatientAllergic(Long patientId, Long medicineId);
	List<Medicine> getSubstitutesOfMedicine(Long id);
	Boolean isMedicineAvailableInPharmacy(Long medicineId, Long pharmacyId);
	List<MedicineDTO> findAllMedicineInPharmacy();
	List<MedicineNameDTO> findAllMedicineNotInPharmacy();
	List<MedicineDTO> searchMedicineInPharmacy(String name);
	List<MedicineNameDTO> findAllMedicine();
    Medicine create(MedicineCreateDTO medicineDTO);
    
	List<DrugType> getAllTypes();

	List<DrugForm> getAllForms();
	List<MedicineDTO> findAllMedicineAvailableInPharmacy(Long pharmacyId);
	
	List<FilteredMedicineDTO> searchMedicineByName(SearchFilterMedicineDTO medicineDTO);
}
