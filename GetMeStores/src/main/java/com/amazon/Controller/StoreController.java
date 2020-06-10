package com.amazon.Controller;


import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.amazon.Datastore.StoresDetails;
import com.amazon.lib.NearByStore;
import com.amazon.util.Constants;
import com.amazon.util.DatabaseFactory;



/*   
 * <h1>Store Controller</h1>
 * It's a front Controller for our Spring MVC app.
 * 
 */

@Controller
public class StoreController {


    private final NearByStore nearByStore;


    /*
     * Constructor Injection used to make requirements of Controller clear.
     */


    @Inject
    public StoreController(final NearByStore nearbystore) {
        this.nearByStore = nearbystore;
    }

    /*
     * Here "/nearbystore" request has been mapped.
     * Which will take to a form where user will enter his/her details and criteria for stores 
     * he/she wants to be retrieved
     * 
     * Lombok - builder pattern has been used to make code more readable and clear.
     * @param ControllerTolibModel is being binded to model.
     * 
     * @return "formTosubmit" will take you to form page
     */
    @RequestMapping(value = Constants.UI_GetMeStores, method = RequestMethod.GET)
    public String display(Model m) {
        final NearByStoreM userDetails = NearByStoreM.builder().build();

        m.addAttribute("user", userDetails);

        return "nearbystore_form";
    }


    /*
     * User entered details will be passed to this method,
     * then it will be validated with constraints from it's model class.
     * 
     * if any validation error, then @return "formTosubmit" - same form again with messages.
     * 
     * else proceed further to retrive storeDetails from the database.
     * 
     */
    @RequestMapping(value = Constants.UI_GetMeStores, method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute final NearByStoreM userDetails, BindingResult errors, Model m) throws Exception {
        if (errors.hasErrors())
            return "nearbystore_form";

        final List < StoresDetails > storedetails = nearByStore.nearbystore(userDetails);

        /*
         * if no stores retrieved, then @return "nostores" - It will dispaly a message saying "no stores within range provided"
         * and ask re-entering details.
         * 
         */
        if (storedetails.size() == 0)
            return "nostores";

        m.addAttribute("details", storedetails);


        /*
         * finally stores retrieved according to user's criteria and will be displayed 
         * on the page "viewStoreDetails".
         * 
         */
        return "viewStoreDetails";
    }
}