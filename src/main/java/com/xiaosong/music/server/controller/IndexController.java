package com.xiaosong.music.server.controller;

import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.error.BizException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
@Tag(name = "测试Controller", description = "这是描述")
public class IndexController {
 
    @GetMapping("/hello")
    @Operation(summary = "测试接口")
    public ResultResponse index( @Parameter(name = "name", description = "名称") @RequestParam(name = "name",required = false) String name) {
        //如果name为空就手动抛出一个自定义的异常！
        if(name==null){
            throw  new BizException("-1","name不能为空！");
        }
        return ResultResponse.success("hello " + name);
    }
 
}