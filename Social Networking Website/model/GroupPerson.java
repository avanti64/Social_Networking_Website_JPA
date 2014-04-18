package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the group_person database table.
 * 
 */
@Entity
@Table(name="group_person")
@NamedQuery(name="GroupPerson.findAll", query="SELECT g FROM GroupPerson g")
public class GroupPerson implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GroupPersonPK id;

	private String flag;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_id")
	private Person person;

	public GroupPerson() {
	}

	public GroupPersonPK getId() {
		return this.id;
	}

	public void setId(GroupPersonPK id) {
		this.id = id;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}