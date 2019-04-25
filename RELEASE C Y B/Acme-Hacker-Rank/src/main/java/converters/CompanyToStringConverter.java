
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Company;

public class CompanyToStringConverter implements Converter<Company, String> {

	@Override
	public String convert(final Company m) {
		String result;

		if (m == null)
			result = null;
		else
			result = String.valueOf(m.getId());
		return result;
	}
}
