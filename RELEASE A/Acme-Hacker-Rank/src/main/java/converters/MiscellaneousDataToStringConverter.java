
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.MiscellaneousData;

public class MiscellaneousDataToStringConverter implements Converter<MiscellaneousData, String> {

	@Override
	public String convert(final MiscellaneousData c) {
		String result;

		if (c == null)
			result = null;
		else
			result = String.valueOf(c.getId());
		return result;
	}
}
