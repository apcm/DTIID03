
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.EducationData;

public class EducationDataToStringConverter implements Converter<EducationData, String> {

	@Override
	public String convert(final EducationData c) {
		String result;

		if (c == null)
			result = null;
		else
			result = String.valueOf(c.getId());
		return result;
	}
}
