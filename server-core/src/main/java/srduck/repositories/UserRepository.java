package srduck.repositories;

import org.springframework.data.repository.CrudRepository;
import srduck.dao.User;

public interface UserRepository extends CrudRepository<User,String> {
    public User findByLogin(String login);
}
