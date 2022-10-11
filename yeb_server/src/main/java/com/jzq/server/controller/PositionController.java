package com.jzq.server.controller;


import com.jzq.server.util.result.RespBean;
import com.jzq.server.pojo.Position;
import com.jzq.server.service.IPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
@RestController
@Api(tags = "职位类接口管理")
@RequestMapping("/api/system/basic/pos")
public class PositionController {

    @Autowired
    private IPositionService positionService;


    @ApiOperation(value = "获取所有职位信息")
    @GetMapping("/")
    public List<Position> getAllPosition() {
        // MybatisPlus的方法，获取所有记录
        return positionService.list();
    }

    @ApiOperation(value = "增加一个职位")
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position) {
        position.setCreateDate(LocalDateTime.now());
        // MybatisPlus的方法，增加一条记录
        if(positionService.save(position)) {
            return RespBean.success("增加职位成功");
        }
        return RespBean.warning("增加职位失败");
    }

    @ApiOperation(value = "修改职位")
    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position){
        // MybatisPlus的方法，修改一条记录
        if (positionService.updateById(position)) {
            return RespBean.success("修改职位信息成功");
        }
        return RespBean.warning("修改职位信息失败");
    }

    @ApiOperation(value = "删除一个职位信息")
    @DeleteMapping("/{id}")
    public RespBean deletePosition(@PathVariable Integer id) {
        if(positionService.removeById(id)) {
            return RespBean.success("删除一个职位成功");
        }
        return RespBean.warning("删除一个职位失败");
    }

    @ApiOperation(value = "删除一组职位信息")
    @DeleteMapping("/")
    public RespBean deletePositions(Integer[] ids) {
        if(positionService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除一组职位成功");
        }
        return RespBean.error("删除一组职位失败");
    }
}
