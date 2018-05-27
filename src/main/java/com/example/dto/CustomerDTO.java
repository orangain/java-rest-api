package com.example.dto;

import java.time.LocalDateTime;

public class CustomerDTO {
	private int customerId;
	private int storeId;
	private String firstName;
	private String lastName;
	private String email;
	private int addressId;
	private boolean active;
	private LocalDateTime createDate;
	private LocalDateTime lastUpdate;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int id) {
		this.customerId = id;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
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

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
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
