package mx.edu.tecdesotware.Market_Backend_Demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.tags.EscapeBodyTag;

@RestController
public class HelloWorldController {

    @GetMapping("/Hello")
    public String saludar(){
        return "Hello World";
    }

}

