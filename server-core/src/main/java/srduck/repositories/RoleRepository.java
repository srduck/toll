package srduck.repositories;

import org.springframework.data.repository.CrudRepository;
import srduck.dao.Role;

public interface RoleRepository extends CrudRepository<Role,String> {
}
