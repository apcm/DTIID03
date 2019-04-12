
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import security.Authority;

@Component
@Transactional
public class StringToAuthorityConverter implements Converter<String, Authority> {

	//@Autowired
	//AuthorityRepository	authorityRepository;

	@Override
	public Authority convert(final String text) {
		Authority res;
		//	final int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				//	id = Integer.valueOf(text);
				//	res = this.authorityRepository.findOne(id);
				res = new Authority();
				res.setAuthority(text);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}
}
