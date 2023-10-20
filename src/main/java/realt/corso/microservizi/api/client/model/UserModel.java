package realt.corso.microservizi.api.client.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserModel {
	
	@NotNull(message ="FFirst Name cannot be ull")
	@Size(min=2 , message = "First Name must be greater than 2 characters")
	private String firstName;
	@NotNull(message ="FFirst Name cannot be ull")
	@Size(min=2 , message = "First Name must be greater than 2 characters")
	private String lastName;
	@NotNull(message ="Password cannot be ull")
	@Size(min=8,max =16, message = "Password must be equal  or greater than 8 characters and less than 16 characters")
	private String password;
	@NotNull(message ="Email cannot be ull")
	@Email
	private String eMail;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

}
