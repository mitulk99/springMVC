package com.amzon;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Hello {
	
	@RequestMapping("/hello")
	public String display(HttpServletRequest req, Model m)
	{
		String pincode=req.getParameter("pincode");
		String radius=req.getParameter("radius");
		String category=req.getParameter("category");
		Back handler=new Back(pincode, radius, category);
		String msg=handler.gets();
		m.addAttribute("message", msg);
		return "viewpage";
	}
}
