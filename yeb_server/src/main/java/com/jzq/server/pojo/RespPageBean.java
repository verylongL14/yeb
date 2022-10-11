package com.jzq.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *  公共分页对象
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespPageBean {
    /**
     * 总条数
      */
    private Long total;

    /**
     * 数据List
     */
    private List<?> data;
}
