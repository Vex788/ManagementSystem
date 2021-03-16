package com.zeus.inc.afinams.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController implements ErrorController {

//    @GetMapping("/admin/{endpoint}")
//    public String onAdminController(@PathVariable(name = "endpoint") String enpoint) {
//    	if (enpoint != null && enpoint.startsWith("/")) { enpoint = enpoint.substring(1); }
//    	System.out.println("admin/" + enpoint);
//        return /*"admin/" + */enpoint;
//    }
//    
//    @GetMapping("/{endpoint}")
//    public String onController(@PathVariable(name = "endpoint") String enpoint) {
//    	if (enpoint != null && enpoint.startsWith("/")) { enpoint = enpoint.substring(1); }
//    	System.out.println("user/" + enpoint);
//        return "user/" + enpoint;
//    }

    @RequestMapping("/admin/error")
    public String handleAdminError(HttpServletRequest request) {
        int errorCode = getErrorCode(request);

        return "admin/" + errorCode;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        int errorCode = getErrorCode(request);

        return "errors_pages/" + errorCode;
    }

    private int getErrorCode(HttpServletRequest request) {
        return (Integer) request.getAttribute("javax.servlet.error.status_code");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/admin/logout")
    public String onLogout(HttpServletRequest request) {
        return "logout";
    }
}