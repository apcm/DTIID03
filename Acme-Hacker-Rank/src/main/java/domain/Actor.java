
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	private String						name;
	private String						surname;
	private String						photo;
	private String						email;
	private String						phoneNumber;
	private String						address;
	private Integer						vat;
	private boolean						flagSpam;
	private boolean						isBanned;
	private Collection<SocialProfile>	socialProfiles;
	private Collection<Box>				boxes;
	private UserAccount					userAccount;


	@OneToMany
	public Collection<Box> getBoxes() {
		return this.boxes;
	}

	public void setBoxes(final Collection<Box> boxes) {
		this.boxes = boxes;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@URL
	@NotBlank
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@NotBlank
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@NotNull
	@Range(min = 0, max = 100)
	public Integer getVat() {
		return this.vat;
	}

	public void setVat(final Integer vat) {
		this.vat = vat;
	}

	public boolean getFlagSpam() {
		return this.flagSpam;
	}

	public void setFlagSpam(final boolean flagSpam) {
		this.flagSpam = flagSpam;
	}

	public boolean getIsBanned() {
		return this.isBanned;
	}

	public void setIsBanned(final boolean isBanned) {
		this.isBanned = isBanned;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<SocialProfile> getSocialProfiles() {
		return this.socialProfiles;
	}

	public void setSocialProfiles(final Collection<SocialProfile> socialProfiles) {
		this.socialProfiles = socialProfiles;
	}

	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
