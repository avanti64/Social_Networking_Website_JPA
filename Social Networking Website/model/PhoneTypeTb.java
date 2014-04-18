package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the phone_type_tb database table.
 * 
 */
@Entity
@Table(name="phone_type_tb")
@NamedQuery(name="PhoneTypeTb.findAll", query="SELECT p FROM PhoneTypeTb p")
public class PhoneTypeTb implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="phone_type_value")
	private String phoneTypeValue;

	//bi-directional many-to-one association to Phone
	@OneToMany(mappedBy="phoneTypeTb")
	private List<Phone> phones;

	public PhoneTypeTb() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhoneTypeValue() {
		return this.phoneTypeValue;
	}

	public void setPhoneTypeValue(String phoneTypeValue) {
		this.phoneTypeValue = phoneTypeValue;
	}

	public List<Phone> getPhones() {
		return this.phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public Phone addPhone(Phone phone) {
		getPhones().add(phone);
		phone.setPhoneTypeTb(this);

		return phone;
	}

	public Phone removePhone(Phone phone) {
		getPhones().remove(phone);
		phone.setPhoneTypeTb(null);

		return phone;
	}

}