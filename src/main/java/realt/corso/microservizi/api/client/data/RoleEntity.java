package realt.corso.microservizi.api.client.data;

import java.io.Serializable;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class RoleEntity implements Serializable {

	
	private static final long serialVersionUID = 7489794509419296769L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	Collection<UserEntity> users;

	@ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinTable(name = "roles_authorities",joinColumns = @JoinColumn(name= "roles_id",referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name="auhorities_id",referencedColumnName = "id") )
	Collection<AuthorityEntity> authorities;
	
	

	public RoleEntity() {
		// TODO Auto-generated constructor stub
	}

	public RoleEntity(String name, Collection<AuthorityEntity> authorities) {
		this.name=name;
		this.authorities=authorities;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(Collection<UserEntity> users) {
		this.users = users;
	}

	public Collection<AuthorityEntity> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<AuthorityEntity> authorities) {
		this.authorities = authorities;
	}

	
}
