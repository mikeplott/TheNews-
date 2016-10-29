package com.theironyard.controllers;

import com.theironyard.entities.Story;
import com.theironyard.entities.User;
import com.theironyard.services.StoryRepo;
import com.theironyard.services.UserRepo;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by michaelplott on 10/29/16.
 */
@Controller
public class TheNewsController {
    @Autowired
    UserRepo users;

    @Autowired
    StoryRepo stories;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session) {
        List<Story> theStories = stories.findAll();

        String name = (String) session.getAttribute("username");
        User user = users.findFirstByUsername(name);

        model.addAttribute("stories", theStories);
        model.addAttribute("username", name);
        return "index";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(String username, String password, HttpSession session) throws Exception {
        User user = users.findFirstByUsername(username);
        if (user == null) {
            user = new User(username, PasswordStorage.createHash(password));
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            throw new Exception("Invalid password");
        }
        session.setAttribute("username", username);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/story", method = RequestMethod.POST)
    public String story(HttpSession session, String headline, String link) throws Exception {
        String name = (String) session.getAttribute("username");
        User user = users.findFirstByUsername(name);
        if (user == null) {
            throw new Exception("Not logged in!");
        }
        Story s = new Story(headline, link, 0, 0, user);
        stories.save(s);
        return "redirect:/";
    }

    @RequestMapping(path = "/real", method = RequestMethod.POST)
    public String realStory(HttpSession session, int id) {
        Story s = stories.findOne(id);
        int realS = s.getReal();
        realS++;
        s.setReal(realS);
        stories.save(s);
        return "redirect:/";
    }

    @RequestMapping(path = "/fake", method = RequestMethod.POST)
    public String fakeStory(HttpSession session, int id) {
        Story s = stories.findOne(id);
        int fakeS = s.getFake();
        fakeS++;
        s.setFake(fakeS);
        stories.save(s);
        return "redirect:/";
    }
}
