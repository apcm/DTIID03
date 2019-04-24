
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Hacker;

public class HackerToStringConverter implements Converter<Hacker, String> {

	@Override
	public String convert(final Hacker h) {
		String result;

		if (h == null)
			result = null;
		else
			result = String.valueOf(h.getId());
		return result;
	}

}
