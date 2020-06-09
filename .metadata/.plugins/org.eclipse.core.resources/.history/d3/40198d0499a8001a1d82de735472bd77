package com.amazon.Controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.amazon.lib.ControllerTolibModel;
import com.amazon.Controller.StoresDetails;
import com.amazon.lib.NearByStoreInterface;


@Controller 
public class StoreController {
	
	
	@Inject
	private NearByStoreInterface getmestore;
	

	
	@RequestMapping(value = "/nearbystore", method = RequestMethod.GET)
	public String display(Model m) throws Exception
	{
		final ControllerTolibModel UR=ControllerTolibModel.builder().build();
		m.addAttribute("user", UR);
		return "hellopage";
	}
	
	
	@RequestMapping(value="/nearbystore", method=RequestMethod.POST)
	public String submit(@ModelAttribute ControllerTolibModel user,Model m) throws Exception
	{
		
		final List<StoresDetails> storedetails=getmestore.getmeStore(user);
		
		if(storedetails.size()==0)
			return "nostores";
		
		m.addAttribute("details", storedetails);
		
		return "viewpage";
	}
}
