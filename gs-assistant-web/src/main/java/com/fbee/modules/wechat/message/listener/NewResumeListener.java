package com.fbee.modules.wechat.message.listener;

import com.fbee.modules.bean.consts.Constants;
import com.fbee.modules.mybatis.dao.TenantsStaffsInfoMapper;
import com.fbee.modules.mybatis.dao.TenantsUserSubscribeMapper;
import com.fbee.modules.mybatis.dao.TenantsUsersMapper;
import com.fbee.modules.mybatis.entity.TenantsUsersEntity;
import com.fbee.modules.mybatis.model.StaffSnapShotInfo;
import com.fbee.modules.mybatis.model.TenantsUserSubscrbes;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.wechat.message.config.WechatConfig;
import com.fbee.modules.wechat.message.model.JobMessageModel;
import com.fbee.modules.wechat.message.model.PushModel;
import com.fbee.modules.wechat.message.model.ResumeMessageModel;
import com.fbee.modules.wechat.message.util.OKHttpUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.soap.Addressing;
import java.util.List;

@Service
public class NewResumeListener extends PushWechatListener {

    private final static Logger log = LoggerFactory.getLogger("messageLogger");

    private JedisTemplate mq = JedisUtils.getJedisMessage();

    @Autowired
    private TenantsStaffsInfoMapper tenantsStaffsInfoMapper;

    @Autowired
    private TenantsUserSubscribeMapper tenantsUserSubscribeMapper;
    @Autowired
    private TenantsUsersMapper tenantsUsersMapper;

    /**
     * 收到简历提醒
     * 门店助手
     *
     * @param channel
     * @param message
     */
    @Override
    public void onMessage(String channel, String message) {
        log.info(String.format("[Listener] new resume: received messageKey [%s] on channel [%s]", message, channel));
        try {
            //等待业务处理事务提交
            Thread.sleep(1000);
            ResumeMessageModel model = JsonUtils.fromJson(message, ResumeMessageModel.class);
            if (StringUtils.isBlank(message) || model == null) {
                log.info("model is null");
                return;
            }
            TenantsUsersEntity user = tenantsUsersMapper.getById(model.getJobTenantUserId());

            StaffSnapShotInfo staff = tenantsStaffsInfoMapper.getSnapshotById(model.getStaffId());
            model.setName(staff.getStaffName().substring(0, 1) + ("01".equals(staff.getSex()) ? "师傅" : "阿姨"));
            model.setOpenid(user.getOpenId());
            model.setExperience(staff.getExperienceValue());

            PushModel data = new PushModel();
            data.setTouser(model.getOpenid());
            data.setTemplateId(WechatConfig.Template.NEW_RESUME_TEMPLATE.getId());
            data.setUrl(Constants.MOBILE_HOST_NAME + "/#/resume/rmDetails/" + model.getResumeId() + "/" + model.getStaffId());
            data.addData("first", model.getTitle() + "\n")
                    .addData("keyword1", model.getJobName(), "#173177")
                    .addData("keyword2", model.getName(), "#173177")
                    .addData("keyword3", model.getExperience(), "#173177")
                    .addData("remark", "\n" + model.getRemark());

            push(data);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }


}
