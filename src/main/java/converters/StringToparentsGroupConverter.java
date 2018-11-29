/*
 * StringToAdministratorConverter.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ParentsGroupRepository;
import domain.ParentsGroup;

@Component
@Transactional
public class StringToparentsGroupConverter implements Converter<String, ParentsGroup> {

	@Autowired
	ParentsGroupRepository	parentsGroupRepository;


	@Override
	public ParentsGroup convert(final String text) {
		ParentsGroup result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.parentsGroupRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
