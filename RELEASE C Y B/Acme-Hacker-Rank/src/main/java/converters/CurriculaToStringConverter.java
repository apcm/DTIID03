
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Curricula;

public class CurriculaToStringConverter implements Converter<Curricula, String> {

	@Override
	public String convert(final Curricula c) {
		String result;

		if (c == null)
			result = null;
		else
			result = String.valueOf(c.getId());
		return result;
	}

}
