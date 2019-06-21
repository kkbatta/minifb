package com.interview.pocketfb.sample.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class WelcomeController {
	
	
	 @RequestMapping("/health")
	    public String index() {
	        return "I am good !! thanks for you concern.. :-)";
	    }
	
	

	
}
