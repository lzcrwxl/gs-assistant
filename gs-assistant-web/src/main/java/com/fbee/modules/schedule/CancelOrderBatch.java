package com.fbee.modules.schedule;

import com.fbee.modules.mybatis.dao.OrdersMapper;
import com.fbee.modules.mybatis.model.Orders;
import com.fbee.modules.service.OrderService;
import com.fbee.modules.utils.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 订单取消批处理
 *
 * @author ZhangJie
 */
@Service
public class CancelOrderBatch extends ScheduledStarter {

    @Autowired
    OrdersMapper orderMapper;

    @Autowired
    OrderService orderService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void executeJob() {
        exec.execute(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });
    }

    private void process() {
        log.info("待支付订金超24小时批处理开始...");
        List<Orders> orders = orderMapper.selectOrderOverTime();
        if (orders == null || orders.size() < 1) {
            return;
        }
        for (Orders order : orders) {
            try{
                orderService.cancelDelayOrder(order);
            }catch (Exception e){
                log.error(String.format("订单取消失败 orderNo [%s]", order.getOrderNo()));
                log.error(e.getMessage());
            }
        }
        log.info("待支付订金超24小时批处理结束...");
    }
}
