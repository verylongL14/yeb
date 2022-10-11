package com.jzq.server.controller;

import com.jzq.server.pojo.Admin;
import com.jzq.server.service.IAdminService;
import com.jzq.server.util.fastDFS.FastDFSUtils;
import com.jzq.server.util.result.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 个人中心
 */

@Api(tags = "个人中心接口管理")
@RestController
@RequestMapping("/api/self/admin")
public class AdminInfoController {

    @Autowired
    private IAdminService adminService;


    @ApiOperation(value = "更新当前用户信息")
    @PutMapping("/info")
    public RespBean updateAdmin(@RequestBody Admin admin, Authentication authentication) {
        if (adminService.updateById(admin)) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin, null, authentication.getAuthorities()));
            return RespBean.success("更新用户信息成功!");
        }

        return RespBean.error("更新用户信息失败!");
    }

    @ApiOperation(value = "更新用户密码")
    @PutMapping("/pwd")
    public RespBean updateAdminPassword(@RequestBody Map<String, Object> info) {

        String oldPass = (String) info.get("oldPass");
        String pass = (String) info.get("pass");
        Integer adminId = (Integer) info.get("adminId");
        return adminService.updateAdminPassword(oldPass, pass, adminId);
    }

    @ApiOperation(value = "更新用户头像")
    @PostMapping("/userface")
    public RespBean updateAdminUserFace(MultipartFile multipartFile, Integer id, Authentication authentication) {
        String[] filePath = FastDFSUtils.upload(multipartFile);
        String url = FastDFSUtils.getTrackerUrl() + filePath[0] + "/" + filePath[1];
        return adminService.updateAdminUserFace(url, id, authentication);
    }
}
