package realt.corso.microservizi.api.client.repository;

import org.springframework.data.repository.CrudRepository;

import realt.corso.microservizi.api.client.data.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

}
