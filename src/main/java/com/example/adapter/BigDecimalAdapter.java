package com.example.adapter;

import java.math.BigDecimal;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BigDecimalAdapter extends XmlAdapter<String, BigDecimal> {

	@Override
	public BigDecimal unmarshal(String v) throws Exception {
		return new BigDecimal(v);
	}

	@Override
	public String marshal(BigDecimal v) throws Exception {
		return v.toString();
	}

}
