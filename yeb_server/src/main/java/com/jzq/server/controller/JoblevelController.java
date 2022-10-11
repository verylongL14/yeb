package com.jzq.server.controller;


import com.jzq.server.util.result.RespBean;
import com.jzq.server.pojo.Joblevel;
import com.jzq.server.service.IJoblevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  职称管理
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
@Api(tags = "职称类接口管理")
@RestController
@RequestMapping("/api/system/basic/joblevel")
public class JoblevelController {


    @Autowired
    private IJoblevelService joblevelService;

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/")
    public List<Joblevel> getAllJobLevels() {
        return joblevelService.list();
    }

    @ApiOperation(value = "添加一个职称")
    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody Joblevel joblevel) {
        joblevel.setCreateDate(LocalDateTime.now());
        System.out.println(joblevel);
        if(joblevelService.save(joblevel)) {
            return RespBean.success("添加职称成功");
        }
        return RespBean.warning("添加职称失败");
    }

    @ApiOperation(value = "修改职称信息")
    @PutMapping("/")
    public RespBean putJobLevel(@RequestBody Joblevel joblevel) {
        if (joblevelService.updateById(joblevel)) {
            return RespBean.success("修改职称信息成功");
        }
        return RespBean.warning("修改职称信息失败");
    }

    @ApiOperation(value = "删除一个职称")
    @DeleteMapping("/{id}")
    public RespBean deleteJoblevel(@PathVariable Integer id) {
        if (joblevelService.removeById(id)) {
            return RespBean.success("删除一个职称成功");
        }
        return RespBean.warning("删除一个职称失败");
    }

    @ApiOperation(value = "删除一组职称")
    @DeleteMapping("/")
    public RespBean deleteJoblevels(Integer[] ids) {
        if (joblevelService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除一组职称成功");
        }
        return RespBean.success("删除一组职称失败");
    }
}
