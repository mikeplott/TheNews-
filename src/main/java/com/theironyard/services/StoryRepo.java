package com.theironyard.services;

import com.theironyard.entities.Story;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by michaelplott on 10/29/16.
 */
public interface StoryRepo extends CrudRepository<Story, Integer> {
    List<Story> findAll();
}
