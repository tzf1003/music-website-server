package com.xiaosong.music.server.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaosong.music.server.domain.User;
import com.xiaosong.music.server.domain.dto.ResultResponse;
import com.xiaosong.music.server.domain.dto.UserInfoDto;
import com.xiaosong.music.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("admin/user")
@Api(value = "用户管理", tags = "管理员相关的接口", description = "UserAdmin")
public class AdminUserController {
    @Autowired
    UserService userService;


    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "管理员搜索用户", notes = "search")
    public ResultResponse adminSearch(@RequestHeader("Authorization") String authHeader,String str,Long page, Long size) {
        //默认
        if (page==null || page<=0){
            page = 10L;
        }
        if (size==null || size<=0){
            size = 10L;
        }
        if (str==null){
            str = "";
        }
        Page<User> userPage = new Page<>(page, size);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", str); // 按照用户名进行搜索
        Page<User> resultPage = userService.page(userPage, queryWrapper);
        Page<UserInfoDto> dtoPage = new Page<>(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
        dtoPage.setRecords(resultPage.getRecords().stream().map(this::convertToDto).collect(Collectors.toList()));
        return ResultResponse.success(dtoPage);
    }
    private UserInfoDto convertToDto(User user) {
        UserInfoDto dto = new UserInfoDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setAvatar(user.getAvatar());
        dto.setDescription(user.getDescription());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole()); // 假设role已正确获取
        return dto;
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "管理员获取用户", notes = "get")
    public ResultResponse get(@RequestHeader("Authorization") String authHeader,@PathVariable Integer id) {
        return ResultResponse.success(convertToDto(userService.getById(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "管理员更新用户", notes = "Put")
    public ResultResponse put(@RequestHeader("Authorization") String authHeader,@PathVariable Integer id,@RequestBody User user) {
        boolean b = userService.updateById(user);
        if (b){
            return ResultResponse.success("","更新成功！");
        }else {
            return ResultResponse.error("更新失败！");
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "管理员删除用户", notes = "Delete")
    public ResultResponse delete(@RequestHeader("Authorization") String authHeader,@PathVariable Integer id) {
        boolean b = userService.removeById(id);
        if (b){
            return ResultResponse.success("","删除成功！");
        }else {
            return ResultResponse.error("删除失败！");
        }
    }
}
