package realt.corso.microservizi.api.client.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import realt.corso.microservizi.api.client.dto.UserDto;

public interface UserService extends UserDetailsService {
	
	public UserDto createUserDTO(UserDto detailUser);
	public UserDto getUserDetailsByUsername(String username);

}
