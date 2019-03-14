/*
 * HomeworkToStringConverter.java
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

import domain.Homework;

@Component
@Transactional
public class HomeworkToStringConverter implements Converter<Homework, String> {

	@Override
	public String convert(final Homework homework) {
		String result;

		if (homework == null)
			result = null;
		else
			result = String.valueOf(homework.getId());

		return result;
	}

}
