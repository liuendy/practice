package demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.common.annotation.ExtModelAttribute;

import demo.model.Demo;

@Controller
@RequestMapping("/demo")
public class DemoController {
	
	/**
	 * 日期格式处理
	 * 
	 * @param binder
	 * @author fanbeibei
	 * @since 2014年9月9日
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// true:允许输入空值，false:不能为空值
	}
	
	@RequestMapping("/hello")
	public ModelAndView hello(){
		ModelAndView mav = new ModelAndView("demoHello");
		return mav;
	}
	
	@RequestMapping("/validate")
	public ModelAndView validate(@Valid @ModelAttribute("demo") Demo demo,BindingResult result){
		ModelAndView mav = new ModelAndView("validate");
		
		if(result.hasErrors()) { //2如果有错误再回到表单展示页面
			System.out.println("******错误信息******");
			System.out.println(result.getFieldErrorCount());
			System.out.println(result.getGlobalErrorCount());
			for (int i = 0; i < result.getFieldErrorCount(); i++) {
				System.out.println(result.getAllErrors().get(i).getDefaultMessage());
			}
			
			System.out.println("******************");
			
			mav.addObject("error", result);
		}
		return mav;
	}
	
	
	@RequestMapping("/dateformate1")
	public ModelAndView dateformate1(@ModelAttribute("demo") Demo demo){
		ModelAndView mav = new ModelAndView("validate");
		
		return mav;
	}
	
	@RequestMapping("/modelDriver")
	public ModelAndView modelDriver(@Valid @ExtModelAttribute("demo") Demo demo,BindingResult result){
		ModelAndView mav = new ModelAndView("modelDriver");
		mav.addObject("demo", demo);
		
		System.out.println("******错误信息******");
		System.out.println(result.getFieldErrorCount());
		System.out.println(result.getGlobalErrorCount());
		for (int i = 0; i < result.getFieldErrorCount(); i++) {
			System.out.println(result.getAllErrors().get(i).getDefaultMessage());
		}
		
		System.out.println("******************");
		
		mav.addObject("error", result);
		return mav;
	}
}
