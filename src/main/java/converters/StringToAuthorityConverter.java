/*
 * StringToCreditCardConverter.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import java.net.URLDecoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import security.Authority;

@Component
@Transactional
public class StringToAuthorityConverter implements Converter<String, Authority> {

	@Override
	public Authority convert(final String text) {
		final Authority res;
		final String parts[];

		if (text == null)
			res = null;
		else
			try {
				parts = text.split("\\|");
				res = new Authority();
				res.setAuthority(URLDecoder.decode(parts[0], "UTF-8"));
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}

		return res;
	}

}
