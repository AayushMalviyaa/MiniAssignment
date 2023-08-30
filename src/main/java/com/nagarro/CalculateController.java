package com.nagarro;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nagarro.service.CalculatorService;


@Controller
public class CalculateController {
	private CalculatorService service;
	
	
	@RequestMapping(value = "/calculate",method = RequestMethod.GET)
	public ModelAndView add(@RequestParam(value ="num1",required = true) double num1,
			@RequestParam(value ="num2",required = true) double num2,
			@RequestParam(value ="operator",required = true) String operator) {
	
		
			service = new CalculatorService();
			String ans = service.calculate(num1, num2, operator);
		
		System.out.println(ans);
		ans = num1+" "+operator+" "+num2+" = "+ ans;
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		mv.addObject("result",ans);
		return mv;
	}
	
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Model m) {
		m.addAttribute("result", "Please Enter Valid Values");
		return "index";
	}

}
