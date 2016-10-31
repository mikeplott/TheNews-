package com.theironyard.controllers;

import com.theironyard.entities.Story;
import com.theironyard.entities.User;
import com.theironyard.services.StoryRepo;
import com.theironyard.services.UserRepo;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public String home(Model model, HttpSession session, Integer page) {
       List<Story> theStories = stories.findAll();
        if (page == null) {
            page = 0;
        }
        PageRequest pr = new PageRequest(page, 5);
        Page<Story> pagedStories;
//        for (Story s : theStories) {
//
//        }
//        for (int i = 0; i < theStories.size(); i++) {
//            pagedStories = stories.findById(pr, i);
//            model.addAttribute("nextPage", page + 1);
//            model.addAttribute("showNext", pagedStories.hasNext());
//            model.addAttribute("prevPage", page - 1);
//            model.addAttribute("showPrev", pagedStories.hasPrevious());
        //}
        String name = (String) session.getAttribute("username");
        User user = users.findFirstByUsername(name);

        for (Story s : theStories) {
          //  for (Story s : pagedStories) {
            String username = s.getUser().getUsername();
            s.isMine = username.equals(name);
            model.addAttribute("isMine", s.isMine);
        }
        //model.addAttribute("stories", pagedStories);
        model.addAttribute("stories", theStories);
        model.addAttribute("username", user);
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
    public String realStory(HttpSession session, int id) throws Exception {
        String name = (String) session.getAttribute("username");
        User user = users.findFirstByUsername(name);
        if (user == null) {
            throw new Exception("Not logged in");
        }
        Story s = stories.findOne(id);
        int realS = s.getReal();
        realS++;
        s.setReal(realS);
        stories.save(s);
        return "redirect:/";
    }

    @RequestMapping(path = "/fake", method = RequestMethod.POST)
    public String fakeStory(HttpSession session, int id) throws Exception {
        String name = (String) session.getAttribute("username");
        User user = users.findFirstByUsername(name);
        if (user == null) {
            throw new Exception("Not logged in");
        }
        Story s = stories.findOne(id);
        int fakeS = s.getFake();
        fakeS++;
        s.setFake(fakeS);
        stories.save(s);
        return "redirect:/";
    }

    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public String profile(HttpSession session, Model model) throws Exception {
        String name = (String) session.getAttribute("username");
        User user = users.findFirstByUsername(name);
        if (user == null) {
            throw new Exception("Not logged in");
        }
        String image = user.getImage();
        List<Story> userStories = stories.findByUserId(user.getId());
        model.addAttribute("name", name);
        model.addAttribute("image", image);
        model.addAttribute("userlist", userStories);
        return "profile";
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadPic(HttpSession session, String image) throws Exception {
        String name = (String) session.getAttribute("username");
        User user = users.findFirstByUsername(name);
        if (user == null) {
            throw new Exception("Not logged in");
        }
        user.setImage(image);
        users.save(user);
//        File dir = new File("public/files");
//        dir.mkdirs();
//        File f = File.createTempFile("file", file.getOriginalFilename(), dir);
//        FileOutputStream fos = new FileOutputStream(f);
//        fos.write(file.getBytes());
//
//        //File file1 = new File(f.getName(), file.getOriginalFilename());
//        //String image = f.getName() + file.getOriginalFilename();
//        //user.setImage(image);
//        Image image = new Image(f.getName(), file.getOriginalFilename(), user);
//        images.save(image);
        return "redirect:/profile";
    }

    @RequestMapping(path = "/story", method = RequestMethod.GET)
    public String createStory(HttpSession session, Model model) throws Exception {
        String name = (String) session.getAttribute("username");
        User user = users.findFirstByUsername(name);
        if (user == null) {
            throw new Exception("Not logged in");
        }
        model.addAttribute("user", user);
        return "story";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public String deleteStory(HttpSession session, int id) throws Exception {
        String name = (String) session.getAttribute("username");
        User user = users.findFirstByUsername(name);
        if (user == null) {
            throw new Exception("Not logged in");
        }
        Story s = stories.findOne(id);
        stories.delete(s);
        return "redirect:/";
    }

    @RequestMapping(path = "/edit", method = RequestMethod.GET)
    public String editStory(HttpSession session, int id, Model model) throws Exception {
        String name = (String) session.getAttribute("username");
        User user = users.findFirstByUsername(name);
        if (user == null) {
            throw new Exception("Not logged in");
        }
        Story s = stories.findOne(id);
        model.addAttribute("story", s);
        return "edit";
    }

    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    public String editTheStory(HttpSession session, int id, String headline, String link) throws Exception {
        String name = (String) session.getAttribute("username");
        User user = users.findFirstByUsername(name);
        if (user == null) {
            throw new Exception("Not logged in");
        }
        Story s = stories.findOne(id);
        if (s.getUser().getId() == user.getId()) {
            s.setHeadline(headline);
            s.setLink(link);
            s.setReal(0);
            s.setFake(0);
            stories.save(s);
        }
        return "/profile";
    }

}
