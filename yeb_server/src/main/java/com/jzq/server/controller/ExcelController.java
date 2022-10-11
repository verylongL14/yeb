package com.jzq.server.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.jzq.server.pojo.*;
import com.jzq.server.service.*;
import com.jzq.server.util.result.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.rmi.server.ExportException;
import java.util.List;

/**
 * 用于处理excel的导出和导入
 */

@Api(tags = "excel导入导出")
@RestController
@RequestMapping("/api")
public class ExcelController {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private INationService nationService;
    @Autowired
    private IPoliticsStatusService politicsStatusService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IJoblevelService joblevelService;
    @Autowired
    private IPositionService positionService;

    @ApiOperation(value = "导出员工表")
    @GetMapping(value = "/export/employee", produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response) {
        List<Employee> list = employeeService.getEmployee(null);
        ExportParams params = new ExportParams("员工表", "员工表", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, Employee.class, list);
        ServletOutputStream out = null;
        try {
            // 流形式
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode("员工表.xls", "UTF-8"));
            out = response.getOutputStream();
            workbook.write(out);
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "导入员工表")
    @PostMapping(value = "/import/employee")
    public RespBean importEmployee(MultipartFile file) {
        ImportParams params = new ImportParams();

        // 去掉标题行
        params.setTitleRows(1);

        List<Nation> nationList = nationService.list();
        List<PoliticsStatus> politicsStatusList = politicsStatusService.list();
        List<Department> departmentList = departmentService.list();
        List<Joblevel> joblevelList = joblevelService.list();
        List<Position> positionList = positionService.list();

        try {
            List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, params);
            list.forEach(employee -> {
                // 民族id
                employee.setNationId(nationList.get(nationList.indexOf(new Nation(employee.getNation().getName()))).getId());
                // 政治面貌id
                employee.setNationId(politicsStatusList.get(politicsStatusList.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
                // 部门id
                employee.setNationId(departmentList.get(departmentList.indexOf(new Department(employee.getDepartment().getName()))).getId());
                // 职称id
                employee.setNationId(joblevelList.get(joblevelList.indexOf(new Joblevel(employee.getJoblevel().getName()))).getId());
                // 职位id
                employee.setNationId(positionList.get(positionList.indexOf(new Position(employee.getPosition().getName()))).getId());
            });

            if (employeeService.saveBatch(list)) {
                return RespBean.success("导入员工信息成功!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return RespBean.warning("导入员工信息失败!");
    }
}
