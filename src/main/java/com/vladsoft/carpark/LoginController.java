package com.vladsoft.carpark;

import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import static com.vladsoft.carpark.UsefulMethods.*;

@Controller
public class LoginController {

    UserAccessManager currentUser;

    static final Logger logger = Logger.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model) {
        model.put("message", "Enter your credentials");
        model.put("color", "green");
        currentUser = null;        
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String handleLoginRequest(@RequestParam String username,
            @RequestParam String password, ModelMap model) {
        currentUser = LoginService.validateUser(username, password);
        if (currentUser != null) {
            logger.info("Main page accessed by: " + (String) currentUser.getUserName());
            model.put("username", currentUser.getUserName());
            model.put("availablemenus", currentUser.printPermits());
            return "main";
        } else {
            currentUser = null;
            logger.error("Access error on user: " + (String) username + " with password " + (String) password);
            model.put("message", "Wrong credentials, try again.");
            model.put("color", "red");
            return "login";
        }
    }

    @RequestMapping(value = "/pagecontent")
    public String handlePageContent(@RequestParam String page, ModelMap model) {

        if (currentUser != null) {
            return currentUser.AccessPage(parseMapIndex(page) - 1);
        } else {
            logger.warn("Invalid access to " + page);
            return "login";
        }
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public String handleQueries(@RequestParam(value = "param[]") String[] args, HttpServletResponse response) {
        String result;

        if (currentUser != null) {
            try {
                result = currentUser.databaseManager.ProcessQuery(args, response);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Fatal Error");
                throw e;
            }
        }
        response.reset();
        return "0";
    }

}
