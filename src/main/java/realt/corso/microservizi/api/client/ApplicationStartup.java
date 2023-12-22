package realt.corso.microservizi.api.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.lang.Arrays;
import jakarta.transaction.Transactional;
import realt.corso.microservizi.api.client.data.AuthorityEntity;
import realt.corso.microservizi.api.client.data.RoleEntity;
import realt.corso.microservizi.api.client.data.UserEntity;
import realt.corso.microservizi.api.client.repository.AuthorityRepository;
import realt.corso.microservizi.api.client.repository.RoleRepository;
import realt.corso.microservizi.api.client.repository.UserRepository;

@Component
public class ApplicationStartup {

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	AuthorityRepository authorityRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional
	@EventListener
	void onEventReadyApplication(ApplicationReadyEvent a) {

		AuthorityEntity write = createAuthorities("WRITE");
		AuthorityEntity read = createAuthorities("READ");
		AuthorityEntity delete = createAuthorities("DELETE");
		
		createRole(Roles.ROLE_USER.name(), java.util.Arrays.asList(read));
		RoleEntity adminRole= createRole(Roles.ROLE_ADMIN.name(), java.util.Arrays.asList(read,write,delete));

		UserEntity userAdmin = new UserEntity();
		userAdmin.setFirstName("admin");
		userAdmin.setLastName("admin");
		userAdmin.setEmail("admin@test.it");
		userAdmin.setUserId(UUID.randomUUID().toString());
		userAdmin.setRoles(java.util.Arrays.asList(adminRole));
		userAdmin.setEncryptedPassword(bCryptPasswordEncoder.encode("12345678"));
		
		userRepository.save(userAdmin);
		
	}

	@Transactional
	private AuthorityEntity createAuthorities(String name) {
		AuthorityEntity authorityEntity = new AuthorityEntity(name);
		authorityRepository.save(authorityEntity);
		return authorityEntity;
	}

	@Transactional
	private RoleEntity createRole(String name, Collection<AuthorityEntity> authorities) {
		RoleEntity role = new RoleEntity(name, authorities);
		roleRepository.save(role);
		return role;
	}
}