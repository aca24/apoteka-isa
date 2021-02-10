package isa.apoteka.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="patient")
public class Patient extends User{

	private static final long serialVersionUID = 1L;

	@ManyToMany(mappedBy = "patients")
	private List<Pharmacy> pharmacies;
	
	//@OneToMany(mappedBy = "patients")
	//private List<Medicine> reservedMedications;
	@OneToMany(mappedBy = "medicine")
	private List<ReservedMedicine> reservedMedicine;

	@JsonIgnore
	@ManyToMany
	private List<Medicine> allergies;

	
	public Patient() {
		super();
	}
	
	public Patient(Long id,String firstName, String lastName) {
		super();
		this.setId(id);
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}

    public Patient(UserRequest userRequest) {
        super(userRequest);
    }
}
