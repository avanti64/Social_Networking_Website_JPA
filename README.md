Social_Networking_Website_JPA
=============================

Developed a website for users to connect with each other on common topics within the discussion forum

Title:

Social Networking Website for Groups

Authors:

Team - DB Chronicles

Team Members -
Aniket Ghodke (ghodke.a@husky.neu.edu)
Avanti Rajiv Kabra (kabra.a@husky.neu.edu)
Swara Saxena (saxena.s@husky.neu.edu)

Abstract:

Web-based social networking services make it possible to connect people who share interests and activities across political, economic, and geographic borders. Through e-mail and instant messaging, online communities are created where a gift economy and reciprocal altruism are encouraged through cooperation. Our website also does the same and links individuals within a group by sharing ideas on common topics. People can connect and share their views on various topics on Media or Sports or any other group and have discussions within the group.

Introduction:

Our website aims to allow users to discuss on a common forum. The following are the goals of this application:

•	Allow users within a group to communicate and connect with each other. 
•	Users can post and comment on each other's posts.
•	Users can belong to one or more groups and be connected on various topics.
•	Allows new user to join the website by signing up.
•	Allows administrator to approve or reject any request by a user to join a particular group.

Our website can be extended by including the functionality to allow administrator to view all users and delete (if needed) users, create groups and allow the users to join any group. As of now these processes are handled by the database administrator as an offline process.





Requirements:

The following are the requirements of the project:

•	Develop a site through which the users can be connected to each other.
•	Discussion board is divided into groups.
•	User can post a new post or discussion thread on approved groups
•	User can comment on existing posts
•	Administrator can view and edit all posts in each of the groups
•	Administrator can approve or reject requests from user to join a new group

These requirements can be illustrated with the help of the following use case diagram.

 

Design:

Our design includes the following classes or tables in the database:

1.	Person : consists of user data, including password in MD5 format.
2.	Groups : consists of the group ids and names of the groups present in our application
3.	Group_Person : has the group id, person id and status of whether the user is approved or not to be a part of the group.
4.	Post : consists of post created by which user and the group to which it belongs
5.	Comment : contains the comments by a particular user with the post id to which it belongs.
6.	Phone : contains the phone number of the user
7.	Phone_Type_tb : contains the type of phone number that the user selects while signing up, be it mobile, work or any other.

To retrieve the comments for each post, we associate the post id as a foreign key in the comment table. To get all posts posted by a particular user, we refer to the person id as the foreign key in post table. Group_Person table is an association class between groups and person table which contains the following:

•	Group id - has the id of the group to which a user requests to join or has joined
•	Person id - user’s id
•	flag - status of the request by a user to join the group. It could be one of - “approved”, “rejected” or “pending”

One can determine the role of a person by selecting the “role” column in person table which would be one of - “User” or “Admin.”

Implementation:

The following software and technologies have been used in the application:

1.	Software interface is a GUI in HTML/CSS and JSP in Eclipse.
2.	The client side validation is performed using javascript.
3.	Middleware consists of two things - RESTFUL API for sign up process and JPA by creating entities and performing all DAO operations
4.	Backend consists of MySQL database

We have used Java as the core programming language and javascript for validations.

Discussion:

Section 1 : GUI and Client side validations -

The GUI is implemented using HTML/CSS and JSP in Eclipse. For client side validations, javascript has been used. One of the examples is the login page which has the following code:


Section 2 : Middleware - JPA -

Our model consisted of all the JPA entities for Comment, Person, Groups , Post, Group_Person, Phone tables. An example of one such entity for Comment is as follows:

package model;

import java.io.Serializable;
import javax.persistence.*;


/**
* The persistent class for the comment database table.
* 
*/
@Entity
@NamedQuery(name="Comment.findAll", query="SELECT c FROM Comment c")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String comment;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_id")
	private Person person;

	//bi-directional many-to-one association to Post
	@ManyToOne
	@JoinColumn(name="p_id")
	private Post post;

	public Comment() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Post getPost() {
		return this.post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}


The controller has the servlet for each of the functionality like login, commenting on posts, posting in groups etc. to enable interaction between the front end and backend to query database. Here we use Entity Manager to query using JPA. Below is an example of a group servlet that displays the comments and posts in a particular group:

package controller;


import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import userDao.UserDao;



/**
* Servlet implementation class GroupServlet
*/
@WebServlet("/GroupServlet")
public class GroupServlet extends HttpServlet {
	private static final String PERSISTENCE_UNIT_NAME = "ws1";
private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private static final long serialVersionUID = 1L;
/**
* @see HttpServlet#HttpServlet()
*/
public GroupServlet() {
super();
// TODO Auto-generated constructor stub
}

	/**
	* @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			this.loginRequest(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


public void loginRequest(HttpServletRequest request,
	HttpServletResponse response) throws ServletException, IOException {
		UserDao dao = new UserDao();
		String group_id = request.getParameter("group_id");

EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
Query  query =em.createNativeQuery
	("select id, post, p.person_id, group_id, first_name from post p, person per where p.group_id=?1 "
			+ "and p.person_id = per.person_id");
		
		Map<Object[], List<Object[]>> map = new HashMap<Object[], List<Object[]>>();
		List<Object[]> obj = (List<Object[]>) query.setParameter(1, group_id).getResultList();
	for (Object[] objs : obj){
		System.out.println("id is" + objs[0] + "Post is"+ objs[1] + "Person id of post is" + objs[2]);
		System.out.println("Post Name is " + objs[4]);
		Query query2 = em.createNativeQuery("select c.comment, c.id, c.person_id, p.first_name from comment c, person p where c.person_id = p.person_id and c.p_id =" + objs[0]);
		List<Object[]> obj2 = (List<Object[]>) query2.getResultList();
		map.put(objs, obj2);
		for (Object[] objs1 : obj2){
			System.out.println("Comments are " + objs1[0] + "Person id is" + objs1[1]);
		}
	}
	
	request.setAttribute("mapData", map);
em.getTransaction().commit();
			em.close();
			RequestDispatcher rd = request.getRequestDispatcher("Group_page.jsp");
			rd.forward(request, response);
	}}


Section 3 : Backend Database -

Our backend consisted of MySQL database used for creating tables and querying to retrieve results and display on the frontend. The following are the create tables used:

create table person
(
person_id int primary key auto_increment,
first_name varchar(50),
user_name varchar(50) unique,
password varchar(50),
role_name varchar(10),
last_name varchar(50),
user_city varchar(50),
user_gender varchar(50),
user_state varchar(50),
user_age int,
user_email varchar(50)
);

create table groups
(  
group_id int primary key auto_increment,
group_name varchar(50)
);

create table post
(
id int primary key auto_increment,
group_id int,
post varchar(2000),
person_id int,
foreign key(person_id) references person(person_id) on update cascade on delete cascade,
foreign key(group_id) references groups(group_id) on update cascade on delete cascade
);


create table comment
(
id int primary key auto_increment,
p_id int,
person_id int,
comment varchar(2000),
foreign key(p_id) references post(id) on update cascade on delete cascade,
foreign key(person_id) references person(person_id) on update cascade on delete cascade
);

create table group_person
(
group_id int,
person_id int,
flag varchar(20),
foreign key(group_id) references groups(group_id) on update cascade on delete cascade,
foreign key(person_id) references person(person_id) on update cascade on delete cascade,
primary key (group_id, person_id)
);


create table phone_type_tb
(
id int primary key auto_increment,
phone_type_value varchar(20)
);

Each of the above tables have their foreign and primary keys which is useful in connecting the tables to retrieve data accordingly. For example to retrieve all comments associated with a particular post, we need to join the comment and the post tables using JOIN in MySQL.

Conclusion:

Our application serves in connecting users together via a common discussion forum. This could be used by a university to connect students from various groups together. For example, Northeastern University could have Undergraduate as one group, Graduate as one group and further groups within these depending on the different fields like Sports, Computer Science, Arts, Media etc. Future scope of our project is to include media like photos, videos upload and have separate administrators for different groups. Our project also has the scope of extension in functionality to make it more robust and user friendly.

References:

1.	http://en.wikipedia.org/wiki/Social_networking_service
2.	http://www.whatissocialnetworking.com/
3.	http://www.w3schools.com/css/
4.	http://www.w3schools.com/js/default.asp
5.	Edward Sciore, “Database Design and Implementation”, 2009.
