package com.nsu.controller;

import com.nsu.comm.Response;
import com.nsu.service.BizLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Monica
 * @Date 2022/10/9 16:05
 **/
@RestController
@RequestMapping("/link")
public class BizLinkController {
    @Autowired
    private BizLinkService linkService;

    @GetMapping("/getAllLink")
    public Response getAllLink(){
        return new Response().success(linkService.getAllLink());
    }
}
