
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	private Date				moment;
	private String				priority;
	private String				tag;
	private String				body;
	private String				subject;
	private boolean				flagSpam;
	private boolean				broadcast;

	private Actor				sender;
	private Collection<Actor>	recipients;	//DEBE SER UNA COLLECTION?


	@NotNull
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getPriority() {
		return this.priority;
	}
	public void setPriority(final String priority) {
		this.priority = priority;
	}

	public String getTag() {
		return this.tag;
	}
	public void setTag(final String tag) {
		this.tag = tag;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}
	public void setBody(final String body) {
		this.body = body;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}
	public void setSubject(final String subject) {
		this.subject = subject;
	}
	public boolean getFlagSpam() {
		return this.flagSpam;
	}
	public void setFlagSpam(final boolean flagSpam) {
		this.flagSpam = flagSpam;
	}

	public boolean isBroadcast() {
		return this.broadcast;
	}

	public void setBroadcast(final boolean broadcast) {
		this.broadcast = broadcast;
	}

	@Valid
	@ManyToOne(optional = false)
	public Actor getSender() {
		return this.sender;
	}
	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	@ManyToMany
	public Collection<Actor> getRecipients() {
		return this.recipients;
	}
	public void setRecipients(final Collection<Actor> recipients) {
		this.recipients = recipients;
	}
	@Override
	public String toString() {
		return "messageerror";
	}

}
