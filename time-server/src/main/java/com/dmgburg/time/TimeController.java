package com.dmgburg.time;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/time")
public class TimeController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String printTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH-mm-ss");
        return dateFormat.format(new Date());
    }
}
