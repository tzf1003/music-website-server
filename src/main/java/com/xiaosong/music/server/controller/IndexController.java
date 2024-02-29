package com.xiaosong.music.server.controller;

import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.error.BizException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
@Api(value = "测试接口", tags = "用户管理相关的接口", description = "用户测试接口")
public class IndexController {
 
    @GetMapping("/hello")
//    @PreAuthorize("hasAuthority('NORMAL_ACCOUNT')")
    @ApiOperation(value = "添加用户", notes = "添加用户")
    @ApiImplicitParam(name = "name", value = "名称")
    public ResultResponse index(@RequestParam(name = "name",required = false) String name) {
        //如果name为空就手动抛出一个自定义的异常！
        if(name==null){
            throw  new BizException("-1","name不能为空！");
        }
        return ResultResponse.success("hello " + name);
    }
 
}