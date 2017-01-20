package com.dmgburg.random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("/random")
public class RandomController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String printTime() {
        return Integer.toString(new Random().nextInt());
    }
}
