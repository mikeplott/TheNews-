package com.theironyard.services;

import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by michaelplott on 10/29/16.
 */
public interface UserRepo extends CrudRepository<User, Integer> {
    User findFirstByUsername(String username);
}
