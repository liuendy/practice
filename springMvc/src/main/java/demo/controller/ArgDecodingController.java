package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/arg")
public class ArgDecodingController {
	@RequestMapping("/test")
	public ModelAndView test(@RequestParam("data") String data) {
		ModelAndView mav = new ModelAndView("ArgChange");
		mav.addObject("data", data);
		return mav;
	}

}
