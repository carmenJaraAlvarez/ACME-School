
package converters;

import java.net.URLEncoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Location;

@Component
@Transactional
public class LocationToStringConverter implements Converter<Location, String> {

	@Override
	public String convert(final Location location) {
		String res;
		StringBuilder builder;

		if (location == null)
			res = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(location.getEastCoordinate(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(location.getNorthCoordinate(), "UTF-8"));
				res = builder.toString();
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}
		return res;
	}
}
