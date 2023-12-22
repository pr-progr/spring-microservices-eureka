package realt.corso.microservizi.api.client.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import realt.corso.microservizi.api.client.data.AuthorityEntity;
import realt.corso.microservizi.api.client.data.RoleEntity;
import realt.corso.microservizi.api.client.data.UserEntity;
import realt.corso.microservizi.api.client.dto.UserDto;
import realt.corso.microservizi.api.client.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	UserRepository userRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;

	
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDto createUserDTO(UserDto userDto) {
		userDto.setUserId(UUID.randomUUID().toString());
		userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		userRepository.save(userEntity);
		UserDto returnDto = modelMapper.map(userEntity, UserDto.class);
		return returnDto;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			
		UserEntity userEntity = userRepository.findByEmail(username);
		
		if(userEntity==null)throw new UsernameNotFoundException("Utente non trovatos");
		
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		Collection<RoleEntity> roles = userEntity.getRoles();
		
		roles.forEach((r) -> {
			authorities.add(new SimpleGrantedAuthority(r.getName()));
			Collection<AuthorityEntity> authoritiesEntity = r.getAuthorities();
			
			authoritiesEntity.forEach((a)->{
				authorities.add(new SimpleGrantedAuthority(a.getName()));
			});
		});
		
		return new org.springframework.security.core.userdetails.User(
				userEntity.getEmail(), userEntity.getEncryptedPassword(),
				true, true, true, true,
				authorities);

	}

	@Override
	public UserDto getUserDetailsByUsername(String username) {
		UserEntity userEntity = userRepository.findByEmail(username);
		if(userEntity==null) throw new UsernameNotFoundException(username);
		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
		return userDto;
	}



}
