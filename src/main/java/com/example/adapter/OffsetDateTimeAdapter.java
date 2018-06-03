package com.example.adapter;

import java.time.OffsetDateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class OffsetDateTimeAdapter extends XmlAdapter<String, OffsetDateTime> {

	@Override
	public OffsetDateTime unmarshal(String v) throws Exception {
		return OffsetDateTime.parse(v);
	}

	@Override
	public String marshal(OffsetDateTime v) throws Exception {
		return v.toString();
	}

}
