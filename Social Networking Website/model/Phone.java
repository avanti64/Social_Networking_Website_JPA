package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the phone database table.
 * 
 */
@Entity
@NamedQuery(name="Phone.findAll", query="SELECT p FROM Phone p")
public class Phone implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="phone_number")
	private String phoneNumber;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_id")
	private Person person;

	//bi-directional many-to-one association to PhoneTypeTb
	@ManyToOne
	@JoinColumn(name="phone_type_id")
	private PhoneTypeTb phoneTypeTb;

	public Phone() {
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public PhoneTypeTb getPhoneTypeTb() {
		return this.phoneTypeTb;
	}

	public void setPhoneTypeTb(PhoneTypeTb phoneTypeTb) {
		this.phoneTypeTb = phoneTypeTb;
	}

}