
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import repositories.CurriculaRepository;
import domain.Curricula;

public class StringToCurriculaConverter implements Converter<String, Curricula> {

	@Autowired
	CurriculaRepository	mr;


	@Override
	public Curricula convert(final String text) {
		Curricula result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.mr.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;

	}

}
