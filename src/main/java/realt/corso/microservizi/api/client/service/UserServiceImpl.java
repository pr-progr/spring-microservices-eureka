package realt.corso.microservizi.api.client.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
	public UserDto createUserDTO(UserDto userDetail) {
		userDetail.setUserId(UUID.randomUUID().toString());
		userDetail.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetail.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userDetail, UserEntity.class);
		userRepository.save(userEntity);
		UserDto returnDto = modelMapper.map(userEntity, UserDto.class);
		return returnDto;
	}



}
