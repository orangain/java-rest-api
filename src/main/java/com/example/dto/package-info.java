@XmlJavaTypeAdapters({ @XmlJavaTypeAdapter(type = OffsetDateTime.class, value = OffsetDateTimeAdapter.class),
		@XmlJavaTypeAdapter(type = BigDecimal.class, value = BigDecimalAdapter.class) })
package com.example.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

import com.example.adapter.BigDecimalAdapter;
import com.example.adapter.OffsetDateTimeAdapter;