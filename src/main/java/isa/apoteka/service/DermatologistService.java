package isa.apoteka.service;

import java.util.List;
import isa.apoteka.domain.Dermatologist;
import isa.apoteka.dto.PharmacyDTO;

public interface DermatologistService {
	String getLogged();
	Dermatologist findById(Long id);
	Dermatologist findByName(String name);
	void hireDerm(Long dermId, Long pharmacyId);
	void fireDerm(Long dermId, Long pharmacyId);
	List<PharmacyDTO> getDermPharmacies(Long dermatologistId);
}
