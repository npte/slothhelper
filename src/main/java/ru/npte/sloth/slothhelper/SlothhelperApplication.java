package ru.npte.sloth.slothhelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class SlothhelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlothhelperApplication.class, args);
	}

    @RequestMapping("/hello")
    @ResponseBody
    public String getHello() {
        return "hello world";
    }
}
