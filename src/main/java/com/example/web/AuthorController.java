package com.example.web;

import com.example.domain.Response;
import com.example.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @RequestMapping(value="/api/author/orderByAmount",method= RequestMethod.GET)
    public Response orderByAmount(@RequestParam int page){
        return authorService.orderByAmount(page,12);
    }

    @RequestMapping(value="/api/author/orderByActivation",method= RequestMethod.GET)
    public Response orderByActivation(@RequestParam int page){
        return authorService.orderByActivation(page,12);
    }

    @RequestMapping(value="/api/author/orderByCitation",method= RequestMethod.GET)
    public Response orderByCitation(@RequestParam int page){
        return authorService.orderByCitation(page,12);
    }

    @RequestMapping(value="/api/author/map",method= RequestMethod.GET)
    public Response getAuthorMap(@RequestParam int id){
        return authorService.getAuthorMap(id);
    }

    @RequestMapping(value="/api/author/network",method= RequestMethod.GET)
    public Response getAuthorNetwork(@RequestParam int id){
        return authorService.getAuthorNetwork(id);
    }

    //获取作者按照g指数排名结果
    @RequestMapping(value="/api/author/orderByGIndex",method=RequestMethod.GET)
    public Response orderByGIndex(@RequestParam int page){return authorService.orderByGIndex(page,12);}
}
