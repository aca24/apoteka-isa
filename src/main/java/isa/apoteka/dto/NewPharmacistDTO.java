package isa.apoteka.dto;

public class NewPharmacistDTO {

	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String address;
	private Long cityId;
	public NewPharmacistDTO() {
		super();
	}
	public NewPharmacistDTO(String firstName, String lastName, String username, String email, String address,
			Long cityId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.address = address;
		this.cityId = cityId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	
	
	
	
	
}
