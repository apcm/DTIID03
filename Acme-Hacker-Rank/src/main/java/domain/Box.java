
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	private String				name;

	private boolean				predefined;

	private Collection<Box>		descendants;

	private Collection<Message>	messages;


	@ManyToMany
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean getPredefined() {
		return this.predefined;
	}

	public void setPredefined(final boolean predefined) {
		this.predefined = predefined;
	}

	@OneToMany
	public Collection<Box> getDescendants() {
		return this.descendants;
	}

	public void setDescendants(final Collection<Box> descendants) {
		this.descendants = descendants;
	}

}
