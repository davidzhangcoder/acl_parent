package com.acl.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @GetMapping("index")
    @ResponseBody
    public String index() {
        return "success";
    }

    @GetMapping("hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @GetMapping("find")
    @ResponseBody
    public String find(){ return "find";
    }

    @GetMapping("findAll")
    @ResponseBody
    public String findAll(){ return "findAll";
    }


    //用注解配置角色
    @GetMapping("findAllAnnotatedRole")
    @Secured("ROLE_managerrole")
    @ResponseBody
    public String findAllAnnotatedRole(){ return "findAllAnnotatedRole";
    }

    //用注解配置权限
    @GetMapping("findAllAnnotatedAuth")
    @PreAuthorize("hasAnyAuthority('admin')")
    @ResponseBody
    public String findAllAnnotatedAuth(){ return "findAllAnnotatedAuth";
    }

    //配置 或者有 角色 或者有 权限
    //配置 或者有managerrole 角色 或者有 sales 权限
    @GetMapping("findAllAnnotatedRoleOrAuth")
    @PreAuthorize("hasRole('managerrole') or hasAuthority('sales')")
    @ResponseBody
    public String findAllAnnotatedRoleOrAuth(){ return "findAllAnnotatedRoleOrAuth";
    }

    //配置 and (即逻辑于的关系)
    @GetMapping("findAllAnnotatedRoleAndAuth")
    @PreAuthorize("hasRole('managerrole') and hasAuthority('sales')")
    @ResponseBody
    public String findAllAnnotatedRoleAndAuth(){ return "findAllAnnotatedRoleAndAuth";
    }



    @GetMapping("loginsuccess")
    @ResponseBody
    public String loginsuccess(){ return "loginsuccess";
    }

    @GetMapping("loginfail")
    @ResponseBody
    public String loginfail(){ return "loginfail";
    }

    @GetMapping("logedout")
    @ResponseBody
    public String logedout(){ return "logedout";
    }


}