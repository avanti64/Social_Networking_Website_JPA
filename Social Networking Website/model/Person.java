package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the person database table.
 * 
 */
@Entity
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="person_id")
	private int personId;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	private String password;

	@Column(name="role_name")
	private String roleName;

	@Column(name="user_age")
	private int userAge;

	@Column(name="user_city")
	private String userCity;

	@Column(name="user_email")
	private String userEmail;

	@Column(name="user_gender")
	private String userGender;

	@Column(name="user_name")
	private String userName;

	@Column(name="user_state")
	private String userState;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="person")
	private List<Comment> comments;

	//bi-directional many-to-one association to GroupPerson
	@OneToMany(mappedBy="person")
	private List<GroupPerson> groupPersons;

	//bi-directional many-to-one association to Phone
	@OneToMany(mappedBy="person")
	private List<Phone> phones;

	//bi-directional many-to-one association to Post
	@OneToMany(mappedBy="person")
	private List<Post> posts;

	public Person() {
	}

	public int getPersonId() {
		return this.personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getUserAge() {
		return this.userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public String getUserCity() {
		return this.userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserGender() {
		return this.userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserState() {
		return this.userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setPerson(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setPerson(null);

		return comment;
	}

	public List<GroupPerson> getGroupPersons() {
		return this.groupPersons;
	}

	public void setGroupPersons(List<GroupPerson> groupPersons) {
		this.groupPersons = groupPersons;
	}

	public GroupPerson addGroupPerson(GroupPerson groupPerson) {
		getGroupPersons().add(groupPerson);
		groupPerson.setPerson(this);

		return groupPerson;
	}

	public GroupPerson removeGroupPerson(GroupPerson groupPerson) {
		getGroupPersons().remove(groupPerson);
		groupPerson.setPerson(null);

		return groupPerson;
	}

	public List<Phone> getPhones() {
		return this.phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public Phone addPhone(Phone phone) {
		getPhones().add(phone);
		phone.setPerson(this);

		return phone;
	}

	public Phone removePhone(Phone phone) {
		getPhones().remove(phone);
		phone.setPerson(null);

		return phone;
	}

	public List<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Post addPost(Post post) {
		getPosts().add(post);
		post.setPerson(this);

		return post;
	}

	public Post removePost(Post post) {
		getPosts().remove(post);
		post.setPerson(null);

		return post;
	}

}