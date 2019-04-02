
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Box;

public class BoxToStringConverter implements Converter<Box, String> {

	@Override
	public String convert(final Box m) {
		String result;

		if (m == null)
			result = null;
		else
			result = String.valueOf(m.getId());
		return result;
	}

}
