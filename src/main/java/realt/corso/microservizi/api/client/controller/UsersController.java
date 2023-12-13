package realt.corso.microservizi.api.client.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import realt.corso.microservizi.api.client.dto.UserDto;
import realt.corso.microservizi.api.client.model.CreatedUserModel;
import realt.corso.microservizi.api.client.model.UserModel;
import realt.corso.microservizi.api.client.service.UserService;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	Environment env;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/status/check")
	public String status() {
		return "Working Users on port " + env.getProperty("local.server.port")  ;
	}
	
	@PostMapping
	public ResponseEntity<CreatedUserModel> createUser(@Valid  @RequestBody UserModel model) {
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = modelMapper.map(model, UserDto.class);
		UserDto userCreateDto = userService.createUserDTO(userDto);
		CreatedUserModel returnValue = modelMapper.map(userCreateDto, CreatedUserModel.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}

}
