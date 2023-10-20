package realt.corso.microservizi.api.client.data;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {
	

		
		/**
		 * 
		 */
		private static final long serialVersionUID = 4961584754343182480L;

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private long id;
		
		@Column(nullable = false , length = 50)
		private String firstName;
		
		@Column(nullable = false , length = 50)
		private String lastName;
		
		@Column(nullable = false , length = 120, unique = true)
		private String eMail;
		
		@Column(nullable = false , unique = true)
		private String userId;
		
		@Column(nullable = false , unique = true)
		private String encryptedPassword;
		
		
		public long getId() {
			return id;
		}
		
		public void setId(long id) {
			this.id = id;
		}
		
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
		
		public String geteMail() {
			return eMail;
		}
		
		public void seteMail(String eMail) {
			this.eMail = eMail;
		}
		
		public String getUserId() {
			return userId;
		}
		
		public void setUserId(String userId) {
			this.userId = userId;
		}
		
		public String getEncryptedPassword() {
			return encryptedPassword;
		}
		
		public void setEncryptedPassword(String encryptedPassword) {
			this.encryptedPassword = encryptedPassword;
		}

}
