/*
 * AdministratorToStringConverter.java
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

import domain.ParentsGroup;

@Component
@Transactional
public class ParentsGroupToStringConverter implements Converter<ParentsGroup, String> {

	@Override
	public String convert(final ParentsGroup parentsGroup) {

		String result;
		if (parentsGroup == null)
			result = null;
		else
			result = String.valueOf(parentsGroup.getId());

		return result;
	}

}
