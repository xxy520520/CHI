package com.example.web;

import com.example.domain.Response;
import com.example.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @RequestMapping(value="/api/field/orderByAmount",method= RequestMethod.GET)
    public Response orderByAmount(@RequestParam int page){
        return fieldService.orderByAmount(page,12);
    }

    @RequestMapping(value="/api/field/orderByActivation",method= RequestMethod.GET)
    public Response orderByActivation(@RequestParam int page){
        return fieldService.orderByActivation(page,12);
    }

    @RequestMapping(value="/api/field/orderByCitation",method= RequestMethod.GET)
    public Response orderByCitation(@RequestParam int page){
        return fieldService.orderByCitation(page,12);
    }

    @RequestMapping(value="/api/field/map",method= RequestMethod.GET)
    public Response getFieldMap(@RequestParam int id){
        return fieldService.getFieldMap(id);
    }
}
