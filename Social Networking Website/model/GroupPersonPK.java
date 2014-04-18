package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the group_person database table.
 * 
 */
@Embeddable
public class GroupPersonPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="group_id", insertable=false, updatable=false)
	private int groupId;

	@Column(name="person_id", insertable=false, updatable=false)
	private int personId;

	public GroupPersonPK() {
	}
	public int getGroupId() {
		return this.groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getPersonId() {
		return this.personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GroupPersonPK)) {
			return false;
		}
		GroupPersonPK castOther = (GroupPersonPK)other;
		return 
			(this.groupId == castOther.groupId)
			&& (this.personId == castOther.personId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.groupId;
		hash = hash * prime + this.personId;
		
		return hash;
	}
}