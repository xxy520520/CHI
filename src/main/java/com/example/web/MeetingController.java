package com.example.web;

import com.example.domain.Response;
import com.example.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @RequestMapping(value="/api/meeting/map",method= RequestMethod.GET)
    public Response getMeetingMap(@RequestParam String name){
        name = name.replaceAll("_"," ");
        return meetingService.getMeetingMap(name);
    }
}
