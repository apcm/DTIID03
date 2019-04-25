
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Position extends DomainEntity {

	private String				title;
	private String				description;
	private Date				deadline;
	private Collection<String>	skills;
	private Collection<String>	technologies;
	private String				profile;
	private boolean				isCancelled;
	private Integer				salary;
	private boolean				finalMode;
	private String				ticker;
	private Company				company;
	private Collection<Problem>	problems;


	@ManyToOne
	@Valid
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}

	@ManyToMany
	public Collection<Problem> getProblems() {
		return this.problems;
	}

	public void setProblems(final Collection<Problem> problems) {
		this.problems = problems;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}

	@ElementCollection(targetClass = String.class)
	public Collection<String> getSkills() {
		return this.skills;
	}

	public void setSkills(final Collection<String> skills) {
		this.skills = skills;
	}
	@ElementCollection(targetClass = String.class)
	public Collection<String> getTechnologies() {
		return this.technologies;
	}

	public void setTechnologies(final Collection<String> technologies) {
		this.technologies = technologies;
	}
	@NotBlank
	public String getProfile() {
		return this.profile;
	}

	public void setProfile(final String profile) {
		this.profile = profile;
	}
	@NotNull
	@Min(1)
	public Integer getSalary() {
		return this.salary;
	}

	public void setSalary(final Integer salary) {
		this.salary = salary;
	}

	public boolean isFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}
	@NotBlank
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public boolean getIsCancelled() {
		return this.isCancelled;
	}

	public void setIsCancelled(final boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

}
