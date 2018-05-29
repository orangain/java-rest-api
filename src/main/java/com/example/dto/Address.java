package com.example.dto;

import java.time.LocalDateTime;

import org.eclipse.persistence.oxm.annotations.XmlElementNillable;

// Mark nillable = true by default.
// See: https://bugs.eclipse.org/bugs/show_bug.cgi?id=368547
@XmlElementNillable(nillable = true)
public class Address {
	private int addressId;
	private String address;
	private String address2;
	private String district;
	private LocalDateTime lastUpdate;

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
