package mg.itu.newapp.controller;

import jakarta.servlet.http.HttpSession;
import mg.itu.newapp.utils.ApiUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class LoginController {


    private ApiUtils apiUtils;

    public LoginController(ApiUtils apiUtils) {
        this.apiUtils = apiUtils;
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "index";
    }

    @PostMapping("/login")
    public String test3(@RequestParam("usr") String usr,@RequestParam("pwd") String pwd,
            HttpSession session, Model model)throws Exception{


        StringBuilder bodyBuilder = new StringBuilder();
        String body = bodyBuilder
                .append("usr=").append(usr)
                .append("&pwd=").append(pwd)
                .toString();

        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        try{
            ResponseEntity<String> response = apiUtils.call(
                    "/api/method/login",
                    HttpMethod.POST,
                    body,
                    String.class,
                    headers
            );

            if(response.getStatusCode() == HttpStatus.OK){
                List<String> header = response.getHeaders().get("Set-Cookie");
                String cookieHeader = header.stream()
                        .map(cookie -> cookie.split(";", 2)[0]) // ne garder que la partie clé=valeur
                        .collect(Collectors.joining("; "));
                session.setAttribute("FRAPPE_CookieHeader", cookieHeader);
                return "home/landing";
            }
        }
        catch(HttpClientErrorException httpClientErrorException){
            if(httpClientErrorException.getStatusCode() == HttpStatus.UNAUTHORIZED){
                model.addAttribute("message", "Invalide Credentials");
                return "index";
            }
        }
        model.addAttribute("message", "Error");
        return "index";
    }


}
