package com.example.help.me.Controllers;

import com.example.help.me.Models.Message;
import com.example.help.me.Models.User;
import com.example.help.me.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
=======
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
>>>>>>> 8e398778abf5036baba162ba24b66bc22bd92552
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
<<<<<<< HEAD
=======
import java.util.Set;
>>>>>>> 8e398778abf5036baba162ba24b66bc22bd92552
import java.util.UUID;

@Controller
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @Value("${upload.path}")
    private String uploadPath;
<<<<<<< HEAD

=======
>>>>>>> 8e398778abf5036baba162ba24b66bc22bd92552

    @GetMapping("/")
    public String helloPage() {
        return "firstpage";
    }


    @GetMapping("/main")
    public String main(Model model,String filter) {
        Iterable<Message> messages = messageRepository.findAll();

        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByMessage(filter);
        } else {
            messages = messageRepository.findAll();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter",filter);
        return "main";
    }

    @PostMapping("/main")
<<<<<<< HEAD
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam String message,
                      @RequestParam String tag, Map<String, Object> model,
                      @RequestParam("file")MultipartFile file) throws IOException {

        Message message1 = new Message(message, tag);

        if (file != null){
           File fileDir = new File(uploadPath);
           if (!fileDir.exists()){
               fileDir.mkdir();
           }
          String uuid =   UUID.randomUUID().toString();

          String resultFilename = uuid + "." + file.getOriginalFilename();
          file.transferTo(new File(uploadPath + "/"+ resultFilename));
            message1.setFilename(resultFilename);
        }
=======
    public String add(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file,
                      @RequestParam String message, Map<String, Object> model) throws IOException {
        Message message1 = new Message(message,user);

        if (file!= null && !file.getOriginalFilename().isEmpty()){
          File uploadDirectory = new File(uploadPath);
          if (!uploadDirectory.exists()){
              uploadDirectory.mkdir();
          }
           String randomid =  UUID.randomUUID().toString();
            String resultname =  randomid + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath +"/" + resultname));

            message1.setFile(resultname);
        }

>>>>>>> 8e398778abf5036baba162ba24b66bc22bd92552
        messageRepository.save(message1);

        Iterable<Message> messages = messageRepository.findAll();

        model.put("messages", messages);

        return "main";
    }
    @GetMapping("/user-messages/{user}")
    public String userMessages(@AuthenticationPrincipal User currentUser,
                               @PathVariable User user,
                               Model model){

        Set<Message> messages = user.getMessageSet();
        model.addAttribute("userChannel",user);
        model.addAttribute("messages",messages);
        model.addAttribute("isCurrentUser",currentUser.equals(user));
        return "userMessages";
    }
    @DeleteMapping("delete")
    public void deleteMessage(@PathVariable Message message){
        messageRepository.deleteByMessage(message.getMessage());
    }
  }


