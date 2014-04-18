package controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import userDao.serviceDao;
import model.Person;
import model.Phone;
@Path("/registration")
public class UserService {
	  @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;
    @POST
	@Path("/")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void create(@FormParam("txtUsrName") String name, @FormParam("txtPassword") String password,
			@FormParam("txtFirstName") String firstName, @FormParam("txtLastName") String lastName,
			@FormParam("txtCity") String city, @FormParam("txtState") String state,@FormParam("txtAge") int age,
			@FormParam("phone_type") int phoneType, @FormParam("txtPhoneNo") String phoneNo,
			@FormParam("txteMail") String email, @FormParam("rdGender") String gender,
			@Context  HttpServletResponse servletResponse) throws IOException, NoSuchAlgorithmException {
		serviceDao dao= new serviceDao();
		Person person = new Person ();
		person.setUserName(name);
		 MessageDigest md = MessageDigest.getInstance("MD5");
		 md.update(password.getBytes());
          byte byteData[] = md.digest();
          StringBuffer sb = new StringBuffer();
		    for (int i = 0; i < byteData.length; i++)
	      sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		 
		person.setPassword(sb.toString());
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setUserAge(age);
		person.setUserEmail(email);
		person.setUserGender(gender);
		person.setUserCity(city);
		person.setUserState(state);
		person.setRoleName("user");
		if (dao.save(person,phoneType,phoneNo) ==0){
			System.out.println("user exists");
			servletResponse.sendRedirect("../Error.html");
		}
		else {
			servletResponse.sendRedirect("../create_todo.html");
		    System.out.println("Welcome user");}
	
	}
}

