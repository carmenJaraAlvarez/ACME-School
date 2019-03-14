/*
 * ParentToStringConverter.java
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

import domain.Parent;

@Component
@Transactional
public class ParentToStringConverter implements Converter<Parent, String> {

	@Override
	public String convert(final Parent parent) {
		String result;

		if (parent == null)
			result = null;
		else
			result = String.valueOf(parent.getId());

		return result;
	}

}
