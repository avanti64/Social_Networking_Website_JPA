package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the groups database table.
 * 
 */
@Entity
@Table(name="groups")
@NamedQuery(name="Group.findAll", query="SELECT g FROM Group g")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="group_id")
	private int groupId;

	@Column(name="group_name")
	private String groupName;

	//bi-directional many-to-one association to GroupPerson
	@OneToMany(mappedBy="group")
	private List<GroupPerson> groupPersons;

	//bi-directional many-to-one association to Post
	@OneToMany(mappedBy="group")
	private List<Post> posts;

	public Group() {
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<GroupPerson> getGroupPersons() {
		return this.groupPersons;
	}

	public void setGroupPersons(List<GroupPerson> groupPersons) {
		this.groupPersons = groupPersons;
	}

	public GroupPerson addGroupPerson(GroupPerson groupPerson) {
		getGroupPersons().add(groupPerson);
		groupPerson.setGroup(this);

		return groupPerson;
	}

	public GroupPerson removeGroupPerson(GroupPerson groupPerson) {
		getGroupPersons().remove(groupPerson);
		groupPerson.setGroup(null);

		return groupPerson;
	}

	public List<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Post addPost(Post post) {
		getPosts().add(post);
		post.setGroup(this);

		return post;
	}

	public Post removePost(Post post) {
		getPosts().remove(post);
		post.setGroup(null);

		return post;
	}

}