package realt.corso.microservizi.api.client.repository;

import org.springframework.data.repository.CrudRepository;

import realt.corso.microservizi.api.client.data.UserEntity;



public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
}
