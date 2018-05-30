package com.example.dto;

import java.time.LocalDateTime;

import org.eclipse.persistence.oxm.annotations.XmlElementNillable;

@XmlElementNillable(nillable = true)
public class Customer {
	// All the properties must be a non-primitive type because this object is also
	// used to represent subset of JSON.For example, PATCH /customers/1 with body
	// {"firstName": "ALICE"} is a valid request. To represent this JSON, all the
	// properties except for firstName should be null not 0 or false.
	private Integer customerId;
	private Integer storeId;
	private String firstName;
	private String lastName;
	private String email;
	private Address address;
	private Boolean active;
	private LocalDateTime createDate;
	private LocalDateTime lastUpdate;

	public Customer() {

	}

	public Customer(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer id) {
		this.customerId = id;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return String.format("customerId=%s, firstName=%s, lastName=%s", this.customerId, this.firstName,
				this.lastName);
	}

}
