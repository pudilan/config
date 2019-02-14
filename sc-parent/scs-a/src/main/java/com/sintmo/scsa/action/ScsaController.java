package com.sintmo.scsa.action;

import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/scsa")
public class ScsaController {

    @RequestMapping("/who")
    public String who(@RequestParam("name") String name) {
        return String.format("I'm scsa,are you %s?", name);
    }

    @RequestMapping("/file/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            file.transferTo(new File("D://upload/" + fileName));
        } catch (IllegalStateException | IOException e) {
            return "fail";
        }
        return "success";
    }

}
