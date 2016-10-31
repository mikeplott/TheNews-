package com.theironyard.services;

import com.theironyard.entities.Image;
import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by michaelplott on 10/29/16.
 */
public interface ImageRepo extends CrudRepository<Image, Integer> {
    Image findByUser(User user);
}
