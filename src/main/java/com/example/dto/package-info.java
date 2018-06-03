@XmlJavaTypeAdapters({ @XmlJavaTypeAdapter(type = OffsetDateTime.class, value = OffsetDateTimeAdapter.class) })
package com.example.dto;

import java.time.OffsetDateTime;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

import com.example.adapter.OffsetDateTimeAdapter;
