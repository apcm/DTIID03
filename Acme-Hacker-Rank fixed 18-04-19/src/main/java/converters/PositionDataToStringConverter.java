
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.PositionData;

public class PositionDataToStringConverter implements Converter<PositionData, String> {

	@Override
	public String convert(final PositionData c) {
		String result;

		if (c == null)
			result = null;
		else
			result = String.valueOf(c.getId());
		return result;
	}
}
