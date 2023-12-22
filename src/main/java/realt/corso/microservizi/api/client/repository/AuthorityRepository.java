package realt.corso.microservizi.api.client.repository;

import org.springframework.data.repository.CrudRepository;

import realt.corso.microservizi.api.client.data.AuthorityEntity;

public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Long> {

}
