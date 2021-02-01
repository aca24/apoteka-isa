package isa.apoteka.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import isa.apoteka.domain.Pharmacist;
import isa.apoteka.domain.Pharmacy;
import isa.apoteka.repository.PharmacistRepository;
import isa.apoteka.service.PharmacistService;

@Service
public class PharmacistServiceImpl implements PharmacistService{

	@Autowired
	private PharmacistRepository pharmacistRepository;
	@Override
	public void firePharm(Long pharmId) {
		pharmacistRepository.firePharm(pharmId);		
	}
	@Override
	public Pharmacist hire(Pharmacist pharmacist) {
		return pharmacistRepository.save(pharmacist);
	}
	@Override
	public Pharmacist findByUsername(String username) {
		return pharmacistRepository.findByUsername(username);
	}
	
	@Override
	public void update(String firstName, String lastName, Long id) {		
		pharmacistRepository.update(firstName, lastName,id);
	}

	@Override
	public Pharmacy getPharmPharmacy(Long pharmacistId) {
		return pharmacistRepository.getPharmacist(pharmacistId).getPharmacy();
	}

	@Override
	public Pharmacist findById(Long id) {
		return pharmacistRepository.findById(id).orElse(null);
	}

}
