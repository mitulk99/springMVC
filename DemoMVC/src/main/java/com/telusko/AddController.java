package com.telusko;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ParseConversionEvent;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.telusko.service.AddService;
import com.telusko.service.Backend;

@Controller
public class AddController {
	@RequestMapping("/hello")
	public ModelAndView add(HttpServletRequest r)
	{
		String pincode=r.getParameter("pincode");
		String radius=r.getParameter("radius");
		String category=r.getParameter("category");
		Backend be=new Backend();
		be.service(pincode, radius, category);
		String s=be.gets();
		ModelAndView mv=new ModelAndView();
		mv.setViewName("display.jsp");
		mv.addObject("result",s);
		return mv;
	}

}
