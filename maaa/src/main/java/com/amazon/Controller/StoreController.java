package com.amazon.Controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
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
	public String display(Model m) 
	{
		final ControllerTolibModel userDetails=ControllerTolibModel.builder().build();
		
		m.addAttribute("user", userDetails);
		
		return "formTosubmit";
	}
	
	
	
	@RequestMapping(value="/nearbystore", method=RequestMethod.POST)
	public String submit( @Valid @ModelAttribute ControllerTolibModel userDetails,BindingResult errors,Model m) throws Exception
	{
		if(errors.hasErrors())
			return "formTosubmit";
		
		final List<StoresDetails> storedetails=getmestore.getmeStore(userDetails);
		
		if(storedetails.size()==0)
			return "nostores";
		
		m.addAttribute("details", storedetails);
		
		return "viewStoreDetails";
	}
}