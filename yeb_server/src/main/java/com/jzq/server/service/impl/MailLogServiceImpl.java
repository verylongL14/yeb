package com.jzq.server.service.impl;

import com.jzq.server.pojo.MailLog;
import com.jzq.server.mapper.MailLogMapper;
import com.jzq.server.service.IMailLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author seven
 * @since 2022-01-02
 */
@Service
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}
