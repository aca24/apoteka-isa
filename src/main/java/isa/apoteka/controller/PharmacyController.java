package isa.apoteka.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import isa.apoteka.domain.Counseling;
import isa.apoteka.domain.Dermatologist;
import isa.apoteka.domain.Medicine;
import isa.apoteka.domain.Pharmacist;
import isa.apoteka.domain.Pharmacy;
import isa.apoteka.domain.PharmacyAdmin;
import isa.apoteka.dto.BusinessDTO;
import isa.apoteka.dto.ChangeDataDTO;
import isa.apoteka.dto.DermatologistDTO;
import isa.apoteka.dto.EmployeeGradeDTO;
import isa.apoteka.dto.PharmacistDTO;
import isa.apoteka.dto.PharmacyDTO;
import isa.apoteka.service.CounselingService;
import isa.apoteka.service.DermatologistGradeService;
import isa.apoteka.service.PharmacistGradeService;
import isa.apoteka.service.PharmacyGradeService;
import isa.apoteka.service.PharmacyService;


@RestController
@RequestMapping(value = "api/pharmacy")
public class PharmacyController {
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private PharmacyGradeService pharmacyGradeService;
	
	@Autowired PharmacistGradeService pharmacistGradeService;
	@Autowired DermatologistGradeService dermatologistGradeService;
	@Autowired CounselingService counselingService;
	
	@GetMapping(value = "/findAll")
	public ResponseEntity<List<PharmacyDTO>> getAllPharmacies() {

		List<Pharmacy> pharmacies = pharmacyService.findAll();

		// convert students to DTOs
		List<PharmacyDTO> pharmacyDTO = new ArrayList<>();
		for (Pharmacy p : pharmacies) {
			pharmacyDTO.add(new PharmacyDTO(p));
		}

		return new ResponseEntity<>(pharmacyDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/findByName")
	public ResponseEntity<PharmacyDTO> getPharmacyByName(@RequestParam String name) {

		Pharmacy pharmacy = pharmacyService.findByName(name);

		// convert students to DTOs
		PharmacyDTO pharmacyDTO = new PharmacyDTO(pharmacy);
		return new ResponseEntity<>(pharmacyDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/findPharmacyForAdmin")
	public ResponseEntity<PharmacyDTO> findPharmacyForAdmin() {
		PharmacyAdmin admin = (PharmacyAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Pharmacy pharmacy = pharmacyService.findById(admin.getPharmacy().getId());
		
		double grade = pharmacyGradeService.findGradeForPharmacy(pharmacy.getId());
		PharmacyDTO pharmacyDTO = new PharmacyDTO();
		pharmacyDTO.setAddress(pharmacy.getStreet());
		pharmacyDTO.setCity(pharmacy.getCity());
		pharmacyDTO.setDescription(pharmacy.getDescription());
		pharmacyDTO.setGrade(grade);
		pharmacyDTO.setId(pharmacy.getId());
		pharmacyDTO.setName(pharmacy.getName());
		return new ResponseEntity<>(pharmacyDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/findAllDermsInPharmacy")
	public ResponseEntity<List<DermatologistDTO>> findAllDermsWorkingInPharmacy(@RequestParam Long id) {

		List<Dermatologist> derms = pharmacyService.findAllDermsWorkingInPharmacy(id);

		List<DermatologistDTO> dermDTO = new ArrayList<>();
		for (Dermatologist d : derms) {
			dermDTO.add(new DermatologistDTO(d));
		}

		return new ResponseEntity<>(dermDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/findAllPharmsInPharmacy")
	public ResponseEntity<List<PharmacistDTO>> findAllPharmsWorkingInPharmacy(@RequestParam Long id) {

		List<Pharmacist> pharms = pharmacyService.findAllPharmsWorkingInPharmacy(id);

		List<PharmacistDTO> pharmDTO = new ArrayList<>();
		for (Pharmacist p : pharms) {
			pharmDTO.add(new PharmacistDTO(p));
		}

		return new ResponseEntity<>(pharmDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/findAllDermsNotInPharmacy")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<DermatologistDTO>> findAllDermsNotWorkingInPharmacy(@RequestParam Long id) {

		List<Dermatologist> derms = pharmacyService.findAllDermsNotWorkingInPharmacy(id);

		List<DermatologistDTO> dermDTO = new ArrayList<>();
		for (Dermatologist d : derms) {
			dermDTO.add(new DermatologistDTO(d));
		}

		return new ResponseEntity<>(dermDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/searchDermsInPharmacy")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<DermatologistDTO>> searchDermsWorkingInPharmacy(@RequestParam Long id, String firstName, String lastName) {

		List<Dermatologist> derms = pharmacyService.searchDermsWorkingInPharmacy(id, firstName, lastName);
		System.out.println("************");
		System.out.println(firstName);
		System.out.println(lastName);
		List<DermatologistDTO> dermDTO = new ArrayList<>();
		for (Dermatologist d : derms) {
			dermDTO.add(new DermatologistDTO(d));
		}

		return new ResponseEntity<>(dermDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/searchPharmsInPharmacy")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<PharmacistDTO>> searchPharmsWorkingInPharmacy(@RequestParam Long id, String firstName, String lastName) {

		List<Pharmacist> pharms = pharmacyService.searchPharmsWorkingInPharmacy(id, firstName, lastName);
		List<PharmacistDTO> pharmDTO = new ArrayList<>();
		for (Pharmacist p : pharms) {
			pharmDTO.add(new PharmacistDTO(p));
		}

		return new ResponseEntity<>(pharmDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/searchMedicineInPharmacy")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Medicine> searchMedicineInPharmacy(Long id, String name) {

		Medicine meds = pharmacyService.searchMedicineInPharmacy(id, name);
		/*List<MedicineDTO> medsDTO = new ArrayList<>();
		for (Medicine m : meds) {
			medsDTO.add(new MedicineDTO(m));
		}*/

		return new ResponseEntity<>(meds, HttpStatus.OK);
	}
	
	@GetMapping(value = "/updateMedicineInPharmacy")
	@PreAuthorize("hasRole('PATIENT')")
	public void updateMedicineInPharmacy(Long pharmId, Long medId, int quantity) {

		pharmacyService.updateMedicineInPharmacy(pharmId, medId, quantity);
		/*List<MedicineDTO> medsDTO = new ArrayList<>();
		for (Medicine m : meds) {
			medsDTO.add(new MedicineDTO(m));
		}*/
	}
	
	
	@GetMapping(value = "/findById")
	public  ResponseEntity<PharmacyDTO> findPharmacyById(@RequestParam Long pharmacyId) {
		Pharmacy pharmacy = pharmacyService.findById(pharmacyId);
		// convert students to DTOs
		double grade = pharmacyGradeService.findGradeForPharmacy(pharmacy.getId());
		PharmacyDTO pharmacyDTO = new PharmacyDTO();
		pharmacyDTO.setAddress(pharmacy.getStreet());
		pharmacyDTO.setCity(pharmacy.getCity());
		pharmacyDTO.setDescription(pharmacy.getDescription());
		pharmacyDTO.setGrade(grade);
		pharmacyDTO.setId(pharmacy.getId());
		pharmacyDTO.setName(pharmacy.getName());
		return new ResponseEntity<>(pharmacyDTO, HttpStatus.OK);
		
	}
	
	@PostMapping(value= "/save", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PharmacyDTO> save(@RequestBody @Valid PharmacyDTO pharmacyDTO) {
		
		pharmacyService.update(pharmacyDTO);
		return new ResponseEntity<>(pharmacyDTO, HttpStatus.CREATED);
		
	}
	
	/*
	@GetMapping(value = "/getReport")
	public  ResponseEntity<BusinessDTO> getReport() {
		PharmacyAdmin admin = (PharmacyAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// convert students to DTOs
		BusinessDTO business = pharmacyGradeService.getPercentage(admin.getPharmacy().getId());
		List<Pharmacist> pharms = pharmacyService.findAllPharmsWorkingInPharmacy(admin.getPharmacy().getId());
		double grade;
		List<EmployeeGradeDTO> pharmacistGrade = new ArrayList<EmployeeGradeDTO>();
		for(Pharmacist p : pharms) {
			grade = pharmacistGradeService.findGradeForPharm(p.getId());
			pharmacistGrade.add(new EmployeeGradeDTO(p.getFirstName(), p.getLastName(), grade));
		}
		
		business.setPharmacistGrades(pharmacistGrade);
		
		List<Dermatologist> derms = pharmacyService.findAllDermsWorkingInPharmacy(admin.getPharmacy().getId());
		List<EmployeeGradeDTO> dermGrade = new ArrayList<EmployeeGradeDTO>();
		for(Dermatologist d : derms) {
			grade = dermatologistGradeService.findGradeForDerm(d.getId());
			dermGrade.add(new EmployeeGradeDTO(d.getFirstName(), d.getLastName(), grade));
		}
		business.setDermatologistGrades(dermGrade);
		Integer[] mesecno = new Integer[12]; 
		Integer[] kvartalno = new Integer[4]; 
		Calendar cal = Calendar.getInstance();
		List<Counseling> counselings = counselingService.finishedCounseling(admin.getPharmacy().getId());
		System.out.println("************");
		System.out.println(counselings.size());
		for(Counseling c : counselings) {
			cal.setTime(c.getStartDate());
			for(int i=0; i<=11; i++) {
				if(cal.get(Calendar.MONTH) == i) {
					if(mesecno[i] == null) {
						mesecno[i] = 1;
					}else {
					mesecno[i] += 1;
					}
					
					if(i>=0 && i<=2) {
						if(kvartalno[0] == null) {
							kvartalno[0] = 1;
						}else {
							kvartalno[0] += 1;
						}
					}else if(i>2 && i<=5) {
						if(kvartalno[1] == null) {
							kvartalno[1] = 1;
						}else {
							kvartalno[1] += 1;
						}
					}else if(i>5 && i<=8) {
						if(kvartalno[2] == null) {
							kvartalno[2] = 1;
						}else {
							kvartalno[2] += 1;
						}
					}else {
						if(kvartalno[3] == null) {
							kvartalno[3] = 1;
						}else {
							kvartalno[3] += 1;
						}
					}
				}
				
			}
			
		}
		business.setQuarterlyCounseling(kvartalno);
		business.setMonthlyCounseling(mesecno);
		return new ResponseEntity<>(business, HttpStatus.OK);
		
	}
	*/
	
	@GetMapping(value = "/getReportByYear")
	public  ResponseEntity<BusinessDTO> getReportByYear(Long year) {
		PharmacyAdmin admin = (PharmacyAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// convert students to DTOs
		BusinessDTO business = pharmacyGradeService.getPercentage(admin.getPharmacy().getId());
		List<Pharmacist> pharms = pharmacyService.findAllPharmsWorkingInPharmacy(admin.getPharmacy().getId());
		double grade;
		List<EmployeeGradeDTO> pharmacistGrade = new ArrayList<EmployeeGradeDTO>();
		for(Pharmacist p : pharms) {
			grade = pharmacistGradeService.findGradeForPharm(p.getId());
			pharmacistGrade.add(new EmployeeGradeDTO(p.getFirstName(), p.getLastName(), grade));
		}
		
		business.setPharmacistGrades(pharmacistGrade);
		
		List<Dermatologist> derms = pharmacyService.findAllDermsWorkingInPharmacy(admin.getPharmacy().getId());
		List<EmployeeGradeDTO> dermGrade = new ArrayList<EmployeeGradeDTO>();
		for(Dermatologist d : derms) {
			grade = dermatologistGradeService.findGradeForDerm(d.getId());
			dermGrade.add(new EmployeeGradeDTO(d.getFirstName(), d.getLastName(), grade));
		}
		business.setDermatologistGrades(dermGrade);
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.DATE, 0);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.YEAR, year.intValue());
		Date startDate = calendar.getTime();
		calendar.add(Calendar.YEAR, 1);
		Date endDate = calendar.getTime();
		
		Integer[] mesecno = new Integer[12]; 
		Integer[] kvartalno = new Integer[4]; 
		Integer[] godisnje = new Integer[2];
		
		Calendar cal = Calendar.getInstance();
		List<Counseling> counselings = counselingService.finishedCounseling(admin.getPharmacy().getId(), startDate, endDate);
		System.out.println("************");
		System.out.println(counselings.size());
		for(Counseling c : counselings) {
			cal.setTime(c.getStartDate());
			for(int i=0; i<=11; i++) {
				if(cal.get(Calendar.MONTH) == i) {
					if(mesecno[i] == null) {
						mesecno[i] = 1;
					}else {
					mesecno[i] += 1;
					}
					
					if(i>=0 && i<=2) {
						if(kvartalno[0] == null) {
							kvartalno[0] = 1;
						}else {
							kvartalno[0] += 1;
						}
					}else if(i>2 && i<=5) {
						if(kvartalno[1] == null) {
							kvartalno[1] = 1;
						}else {
							kvartalno[1] += 1;
						}
					}else if(i>5 && i<=8) {
						if(kvartalno[2] == null) {
							kvartalno[2] = 1;
						}else {
							kvartalno[2] += 1;
						}
					}else {
						if(kvartalno[3] == null) {
							kvartalno[3] = 1;
						}else {
							kvartalno[3] += 1;
						}
					}
				}
				
			}
			
		}
		List<Counseling> allcounselings = counselingService.AllfinishedCounseling(admin.getPharmacy().getId());
		for(Counseling c : allcounselings) {
			cal.setTime(c.getStartDate());
			if(cal.get(Calendar.YEAR) == 2020) {
				if(godisnje[0] == null) {
					godisnje[0] = 1;
				}else {
				godisnje[0] += 1;
				}
			}else {
				if(godisnje[1] == null) {
					godisnje[1] = 1;
				}else {
				godisnje[1] += 1;
				}
			}
		}
		business.setQuarterlyCounseling(kvartalno);
		business.setMonthlyCounseling(mesecno);
		business.setYearlyCounseling(godisnje);
		
		//BusinessDTO b = medicineUsage(business);
		
		return new ResponseEntity<>(business, HttpStatus.OK);
		
	}
/*
	private BusinessDTO medicineUsage(BusinessDTO business) {
		PharmacyAdmin admin = (PharmacyAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.DATE, 0);
		calendar.set(Calendar.MONTH, 0);
		Date startDate = calendar.getTime();
		calendar.add(Calendar.YEAR, 1);
		Date endDate = calendar.getTime();
		
		Integer[] mesecno = new Integer[12]; 
		Integer[] kvartalno = new Integer[4]; 
		Integer[] godisnje = new Integer[2];
		
		Calendar cal = Calendar.getInstance();
		List<Counseling> counselings = counselingService.finishedCounseling(admin.getPharmacy().getId(), startDate, endDate);
		System.out.println("************");
		System.out.println(counselings.size());
		for(Counseling c : counselings) {
			cal.setTime(c.getStartDate());
			for(int i=0; i<=11; i++) {
				if(cal.get(Calendar.MONTH) == i) {
					if(mesecno[i] == null) {
						mesecno[i] = 1;
					}else {
					mesecno[i] += 1;
					}
					
					if(i>=0 && i<=2) {
						if(kvartalno[0] == null) {
							kvartalno[0] = 1;
						}else {
							kvartalno[0] += 1;
						}
					}else if(i>2 && i<=5) {
						if(kvartalno[1] == null) {
							kvartalno[1] = 1;
						}else {
							kvartalno[1] += 1;
						}
					}else if(i>5 && i<=8) {
						if(kvartalno[2] == null) {
							kvartalno[2] = 1;
						}else {
							kvartalno[2] += 1;
						}
					}else {
						if(kvartalno[3] == null) {
							kvartalno[3] = 1;
						}else {
							kvartalno[3] += 1;
						}
					}
				}
				
			}
			
		}
		List<Counseling> allcounselings = counselingService.AllfinishedCounseling(admin.getPharmacy().getId());
		for(Counseling c : allcounselings) {
			cal.setTime(c.getStartDate());
			if(cal.get(Calendar.YEAR) == 2020) {
				if(godisnje[0] == null) {
					godisnje[0] = 1;
				}else {
				godisnje[0] += 1;
				}
			}else {
				if(godisnje[1] == null) {
					godisnje[1] = 1;
				}else {
				godisnje[1] += 1;
				}
			}
		}
		business.setQuarterlyCounseling(kvartalno);
		business.setMonthlyCounseling(mesecno);
		business.setYearlyCounseling(godisnje);
	}
	*/
}
