package com.example.web;

import com.example.domain.Response;
import com.example.service.AffiliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AffiliationController {

    @Autowired
    private AffiliationService affiliationService;

    @RequestMapping(value="/api/affiliation/orderByAmount",method= RequestMethod.GET)
    public Response orderByAmount(@RequestParam int page){
        return affiliationService.orderByAmount(page,12);
    }

    @RequestMapping(value="/api/affiliation/orderByActivation",method= RequestMethod.GET)
    public Response orderByActivation(@RequestParam int page){
        return affiliationService.orderByActivation(page,12);
    }

    @RequestMapping(value="/api/affiliation/orderByCitation",method= RequestMethod.GET)
    public Response orderByCitation(@RequestParam int page){
        return affiliationService.orderByCitation(page,12);
    }

    @RequestMapping(value="/api/affiliation/map",method= RequestMethod.GET)
    public Response getAffiliationMap(@RequestParam int id){
        return affiliationService.getAffiliationMap(id);
    }

    @RequestMapping(value="/api/affiliation/network",method= RequestMethod.GET)
    public Response getAffiliationNetwork(@RequestParam int id){
        return affiliationService.getAffiliationNetwork(id);
    }

    //获取作者按照g指数排名结果
    @RequestMapping(value="/api/affiliation/orderByGIndex",method=RequestMethod.GET)
    public Response orderByGIndex(@RequestParam int page){return affiliationService.orderByGIndex(page,12);}
}
