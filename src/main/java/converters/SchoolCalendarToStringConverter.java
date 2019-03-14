/*
 * SchoolCalendarToStringConverter.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SchoolCalendar;

@Component
@Transactional
public class SchoolCalendarToStringConverter implements Converter<SchoolCalendar, String> {

	@Override
	public String convert(final SchoolCalendar schoolCalendar) {

		String result;
		if (schoolCalendar == null)
			result = null;
		else
			result = String.valueOf(schoolCalendar.getId());

		return result;
	}

}
