
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.PersonalData;

public class PersonalDataToStringConverter implements Converter<PersonalData, String> {

	@Override
	public String convert(final PersonalData c) {
		String result;

		if (c == null)
			result = null;
		else
			result = String.valueOf(c.getId());
		return result;
	}
}
