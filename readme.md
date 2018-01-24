# 家策好服务 - 门店助手 服务端

--------------------


update:

```



CREATE TABLE `tenants_user_subscribes` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_user_id` int not null comment '门店用户id',
  `openid` varchar(100) not null comment '用户openid',
  `user_name` varchar(100) not null comment '用户openid',
  `channel` varchar(100) not null comment 'channel, e.g.  new job/new resume',
  `receive_time` varchar(10) null comment '接收推送时间段',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='订阅信息表';


CREATE TABLE `tenants_user_subscribe_areas` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sub_id` int(11) NOT NULL COMMENT '订阅id',
  `province` varchar(100) not null comment '订阅省',
  `city` varchar(100) not null comment '订阅市',
  `district` varchar(100) default null comment '订阅区',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='订阅地区表';


CREATE TABLE `tenants_user_subscribe_subjects` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sub_id` int(11) NOT NULL COMMENT '订阅id',
  `subject` varchar(100) not null comment '订阅主题(e.g. job:月嫂/育儿嫂/··)',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='订阅服务主题表';



```


> api

* get  /api/common/getDictionaryData/c035  时间区间查询（按月按天按小时）
* get  /api/job/info    检查发布条件（发布次数，金额信息）
* post /api/job          创建职位，生成招聘信息
* get  /api/job          查询职位招聘列表(可抢单)  **update**
* get  /api/job/resume/mine/apply          查询我投递的简历列表(处理中/已完成/已拒绝/已取消) **update**
* get  /api/job/{jobId}  查询职位招聘详情
* post /api/job/refresh?id=1,2,3  刷新职位
* post /api/job/{jobId}/resume  抢单，投递阿姨简历
* get  /api/job/resume/mine/box  查询我收到的所有简历（简历箱）**update**
* get  /api/job/resume/{resumeId} 查询简历详情 (合作抢单  详情页 )   **update**
* post /api/job/{jobId}/resume/{resumeId} 审核通过简历


> condition 查询参数

| name | type | desc |
|--------|-------|------|
|serviceType|string|服务工种|
|resumeStatus|string| 12处理中（1待处理，2待面试）, 4已完成， 35(3已拒绝, 5已取消）
|salaryType|string|薪酬单位|
|salaryRange|string|薪酬区间01/02/03/04/05|
|age|string|年龄要求|
|serviceProvince|string|省|
|orderNo|string|订单编号|
|jobStatus|string|1上架0下架|
|withOutSelf|int|不看自己1是0否|
|onlySelf|int|只看自己 1是0否|
|isApplyable|int|查询可抢单(抢单数<5) 1是0否|


> model  职位招聘模型

| name | type | desc |
|--------|-------|------|
|id |int||
|tenantId|int||
|tenantUserId|int||
|contactName|string|联系人|
|contactPhone|string|联系电话|
|jobTenantName|string|家政公司名称|
|orderNo|string|订单编号|
|serviceType|string|服务工种->serviceTypeValue|
|serviceMold|string|服务类型->serviceMoldValue|
|age|string|年龄要求->ageValue|
|salaryType|string|薪酬单位->salaryTypeValue|
|salary|decimal|薪酬|
|demand|string|要求|
|skillRequirements|array|技能要求|
|serviceStartTime|string|服务开始时间|
|serviceEndTime|string|服务结束时间|
|totalNum|int|可应聘总人数|
|usedNum|int|已应聘人数|
|emsSign|string|01急02否|
|serviceProvince|string|省->serviceProvinceValue|
|serviceCity|string|市->serviceCityValue|
|serviceArea|string|市->serviceAreaValue|
|orderStatus|string|订单状态（待处理／待面试／已完成／已取消）|
|status|int|状态1上架／0下架|
|isRefreshed|int|是否已刷新1是0否|
|finishedTime|string|结单日期|
|addTime|string|添加时间|
|addAccount|string|添加人|
|modifyTime|string|修改时间|
|modifyAccount|string|修改人|

> model 简历

> condition 查询参数

| name | type | desc |
|--------|-------|------|
|serviceType|string|服务工种|
|status|string| 12处理中（1待处理，2待面试）, 4已完成， 35(3已拒绝, 5已取消）  **update**|
|jobStatus|string|职位状态：1上架/0下架|
|salaryType|string|薪酬单位|
|age|string|年龄要求|
|serviceProvince|string|省|
|orderNo|string|订单编号|
|applyStartDate|date|投递时间开始|
|applyEndDate|date|投递时间结束|
|queryUserId|string|查询业务员loginName|


|name | type | desc|
|-----|------|-----|
|id | int ||
|jobId | int | 招聘职位ID|
|orderNo | string |订单编号|
|jobTenantId|int|招聘方门店id|
|jobTenantUserId|int|招聘方门店用户id|
|resumeTenantId|int|应聘方门店id|
|resumeTenantUserId|int|应聘方门店用户id|
|resumeTenantStaffId|int|应聘投递阿姨id|
|contactName|string|联系人|
|contactPhone|string|联系电话|
|resumeTenantName|string|家政公司名称|
|applyDate|string|简历投递时间|
|checkDate|string|简历审核时间|
|status|string|状态1待处理，2待面试，3已拒绝, 4已完成， 5已取消|
|match|int|匹配度|
|remarks|string|备注|
|staffInfo|Object:StaffInfo|阿姨详细信息|
|jobInfo|Object:JobInfo|职位详细信息|
|addTime|string|添加时间|
|addAccount|string|添加人|
|modifyTime|string|修改时间|
|modifyAccount|string|修改人|
