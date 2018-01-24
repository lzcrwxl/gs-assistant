# 网站管理接口文档
## 凡是标识 上一个版本接口 请求都按照上一个版本请求

/*2018-01-05 lijia*/

ALTER TABLE `tenants_users` 
ADD COLUMN `refresh` INT(1) NULL DEFAULT 0 COMMENT '手机端判断是否需要刷新' AFTER `nick_name`;


/*2017-12-07 lijia*/
ALTER TABLE `tenants_users` 
ADD COLUMN `open_id` VARCHAR(100) NULL DEFAULT NULL COMMENT '微信用户openId' AFTER `tenant_id`;
ALTER TABLE `tenants_users` 
ADD COLUMN `union_id` VARCHAR(100) NULL DEFAULT NULL AFTER `open_id`;
ALTER TABLE `tenants_users` 
ADD COLUMN `nick_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '绑定微信用户昵称' AFTER `union_id`;


## 一、网站管理--首页管理
### 请求名称：/api/website/getIndexInfo
### 请求方式：get/post
### 请求参数：无
### 响应案例：
               {
    "success": true,
    "code": 0,
    "msg": "成功",
    "toUrl": null,
    "jsonData": {
        "defaultImage": "http://xianghengzi01.xicp.net/xxx.png",
        "bannnerImage": "",
        "sIndexInfoJsons": [
            {
                "tenantId": null,
                "serviceItemName": "月嫂",
                "servicePrice": 58888,
                "serviceDesc": "对自己好一点，给宝宝多一点",
                "imageUrl": "http:www.tests.test",
                "isHot": null,
                "serviceObject": null,
                "serviceContent": null,
                "isShow": null,
                "servicePriceUnit": "03",
                "isDefault": "1",
                "serialNumber": 1,
                "priceValue": "元起/26天",
                "serviceItemCode": "01"
            },
            {
                "tenantId": null,
                "serviceItemName": "育儿嫂",
                "servicePrice": 6888,
                "serviceDesc": "科学育儿，快乐成长",
                "imageUrl": "/website/serviceitem/20170323101055565.jpg",
                "isHot": null,
                "serviceObject": null,
                "serviceContent": null,
                "isShow": null,
                "servicePriceUnit": "01",
                "isDefault": "0",
                "serialNumber": 2,
                "priceValue": "元起/月",
                "serviceItemCode": "02"
            },
            {
                "tenantId": null,
                "serviceItemName": "保姆",
                "servicePrice": 4888,
                "serviceDesc": "一心为您，才能懂你所需",
                "imageUrl": "/website/serviceitem/20170323214502216.jpg",
                "isHot": null,
                "serviceObject": null,
                "serviceContent": null,
                "isShow": null,
                "servicePriceUnit": "01",
                "isDefault": "1",
                "serialNumber": 3,
                "priceValue": "元起/月",
                "serviceItemCode": "03"
            },
            {
                "tenantId": null,
                "serviceItemName": "老人陪护",
                "servicePrice": 5888,
                "serviceDesc": "24小时无微不至，贴身照顾",
                "imageUrl": "/website/serviceitem/20170321160251610.jpg",
                "isHot": null,
                "serviceObject": null,
                "serviceContent": null,
                "isShow": null,
                "servicePriceUnit": "01",
                "isDefault": "1",
                "serialNumber": 4,
                "priceValue": "元起/月",
                "serviceItemCode": "04"
            },
            {
                "tenantId": null,
                "serviceItemName": "钟点工",
                "servicePrice": 50,
                "serviceDesc": "摆脱繁琐家务，重获生活自由",
                "imageUrl": "/website/serviceitem/20170321160535150.jpg",
                "isHot": null,
                "serviceObject": null,
                "serviceContent": null,
                "isShow": null,
                "servicePriceUnit": "02",
                "isDefault": "1",
                "serialNumber": 5,
                "priceValue": "元起/小时",
                "serviceItemCode": "05"
            },
            {
                "tenantId": null,
                "serviceItemName": "家庭管家",
                "servicePrice": 4888,
                "serviceDesc": "全能管家，贴心服务",
                "imageUrl": "/website/serviceitem/20170321160736627.jpg",
                "isHot": null,
                "serviceObject": null,
                "serviceContent": null,
                "isShow": null,
                "servicePriceUnit": "04",
                "isDefault": "0",
                "serialNumber": 6,
                "priceValue": "元起/天",
                "serviceItemCode": "06"
                }
            ]
         }
    }

## 二、网站管理--banner管理--首页(上一个版本接口)
### 请求名称：/api/banner/getBannerInfo
### 请求方式：get/post
### 请求参数：无
### 响应案例：
             {
    "success": true,
    "code": 0,
    "msg": "成功",
    "toUrl": null,
    "jsonData": {
        "id": 1,
        "tenantId": 1,
        "bannerName": null,
        "bannerUrl": "http://ysc-b.jiacer.com/img/siteManagement/banner.png",
        "sortNo": null,
        "status": "00",
        "shelfType": "00",
        "shelfTime": null,
        "isDefault": "0",
        "remarks": null,
        "isUsable": "1",
        "addTime": null,
        "addAccount": null,
        "modifyTime": 1505862683000,
        "modifyAccount": null
        }
    }
## 三、网站管理--banner管理-保存并发布
### 请求名称：/api/banner/save
### 请求方式：get/post
### 请求参数：
请求参数 | 类型 | 必填|备注
---|---|---|---
 bannerPath|String  |非必填 |banner图片地址
isDefault | String  |必填|是否默认0 1，为0时后台设置banner默认地址
### 响应案例：
                {
    "success": true,
    "code": 0,
    "msg": "成功",
    "toUrl": null,
    "jsonData": 0
    }

## 四、网站管理--关于我们-首页(上一个版本接口)
### 请求名称：/api/website/queryAboutUsInfo
### 请求方式：get/post
### 请求参数：无
### 响应案例：
               {
    "success": true,
    "code": 0,
    "msg": "成功",
    "toUrl": null,
    "jsonData": {
        "id": 26358832,
        "tenantId": 1,
        "content": "http://ysc-b.jiacer.com/img/siteManagement/banner.png",
        "images": "http://test.jiacersxy.com:8000/website/aboutus/20170919152522279.jpeg",
        "addTime": 1495182123000,
        "addAccount": "zhangsan",
        "modifyTime": 1505805922000,
        "modifyAccount": "zhangsan",
        "isDefault": "Z",
        "isUsable": "0"
        }
    }

## 五、网站管理--关于我们--保存
### 请求名称：/api/website/saveAboutUsInfo
### 请求方式：get/post
### 请求参数：

请求参数 | 类型| 必填|备注
---|---|---|---
 content| String|必填|图片地址和内容一起上传
### 响应案例：
                {
    "success": true,
    "code": 0,
    "msg": "成功",
    "toUrl": null,
    "jsonData": {
        "success": true,
        "code": 0,
        "msg": "成功",
        "toUrl": null,
        "jsonData": 0
        }
    }               
## 六、网站管理--服务管理--详情页
### 请求接口：/api/website/getServiceManageDetails
### 请求方式：get/post
### 请求参数: 无
### 响应案例：
               {
    "success": true,
    "code": 0,
    "msg": "成功",
    "toUrl": null,
    "jsonData": {
        "success": true,
        "code": 0,
        "msg": "成功",
        "toUrl": null,
        "jsonData": [
            {
                "tenantId": null,
                "serviceItemName": "月嫂",
                "servicePrice": 58888,
                "serviceDesc": "对自己好一点，给宝宝多一点",
                "imageUrl": "http:www.tests.test",
                "isHot": "0",
                "serviceObject": "孕产妇、婴儿",
                "serviceContent": "月子餐、产妇护理、婴儿照料、月子餐、产妇护理、婴儿照料、月子餐、产妇护理、婴儿照料、月子餐、产妇护",
                "isShow": "0",
                "servicePriceUnit": "03",
                "isDefault": "1",
                "serialNumber": 1,
                "priceValue": "元起/26天",
                "serviceItemCode": "01"
            },
            {
                "tenantId": null,
                "serviceItemName": "育儿嫂",
                "servicePrice": 6888,
                "serviceDesc": "科学育儿，快乐成长",
                "imageUrl": "/website/serviceitem/20170323101055565.jpg",
                "isHot": "1",
                "serviceObject": "0-3岁宝宝",
                "serviceContent": "智力开发、宝宝生活照料",
                "isShow": "1",
                "servicePriceUnit": "01",
                "isDefault": "0",
                "serialNumber": 2,
                "priceValue": "元起/月",
                "serviceItemCode": "02"
            },
            {
                "tenantId": null,
                "serviceItemName": "保姆",
                "servicePrice": 4888,
                "serviceDesc": "一心为您，才能懂你所需",
                "imageUrl": "/website/serviceitem/20170323214502216.jpg",
                "isHot": "1",
                "serviceObject": "家庭和个人",
                "serviceContent": "打扫、做饭",
                "isShow": "1",
                "servicePriceUnit": "01",
                "isDefault": "1",
                "serialNumber": 3,
                "priceValue": "元起/月",
                "serviceItemCode": "03"
            },
            {
                "tenantId": null,
                "serviceItemName": "老人陪护",
                "servicePrice": 5888,
                "serviceDesc": "24小时无微不至，贴身照顾",
                "imageUrl": "/website/serviceitem/20170321160251610.jpg",
                "isHot": "0",
                "serviceObject": "老人、患者",
                "serviceContent": "陪护、生活照料、起居照料",
                "isShow": "1",
                "servicePriceUnit": "01",
                "isDefault": "1",
                "serialNumber": 4,
                "priceValue": "元起/月",
                "serviceItemCode": "04"
            },
            {
                "tenantId": null,
                "serviceItemName": "钟点工",
                "servicePrice": 50,
                "serviceDesc": "摆脱繁琐家务，重获生活自由",
                "imageUrl": "/website/serviceitem/20170321160535150.jpg",
                "isHot": "1",
                "serviceObject": "家庭、个人、公寓",
                "serviceContent": "日常保洁、开荒打扫",
                "isShow": "1",
                "servicePriceUnit": "02",
                "isDefault": "1",
                "serialNumber": 5,
                "priceValue": "元起/小时",
                "serviceItemCode": "05"
            },
            {
                "tenantId": null,
                "serviceItemName": "家庭管家",
                "servicePrice": 4888,
                "serviceDesc": "全能管家，贴心服务",
                "imageUrl": "/website/serviceitem/20170321160736627.jpg",
                "isHot": "1",
                "serviceObject": "家庭",
                "serviceContent": "接待客人、开车、接送小孩",
                "isShow": "1",
                "servicePriceUnit": "04",
                "isDefault": "0",
                "serialNumber": 6,
                "priceValue": "元起/天",
                "serviceItemCode": "06"
                }
            ]
        }
    }
## 七、网站管理--服务管理—-保存(上一个版本接口)
### 请求接口：/api/website/updateServiceManageDetalisInfo
### 请求方式：get/post
### 请求参数：
ServiceManageIndexInfoJson对象：
请求参数  | 类型|必填|备注
---|---|---|---
serviceItemName | String|非必填|服务工种
servicePrice | double|非必填|服务价格
serviceDesc|String|非必填|服务描述
imageUrl|String|非必填|服务图片，默认就不填
isHot|String|非必填|是否hot，默认请传null或空字符串
serviceObject|String|非必填|服务对象
serviceContent|String|非必填|服务内容
servicePriceUnit|String|非必填|服务单位
isShow|String|非必填|是否显示。默认请传null或空字符串
isDefault|String|非必填|是否使用默认图片0或1，默认请传null或空字符串
serialNumber|int|非必填|排序
servicePriceUnit|String|非必填|价格单位code 如：03
serviceItemCode|String|必填|服务项目代码。如有任何信息都必须填，无任何修改不填
### 响应案例：
                {
    "success": true,
    "code": 0,
    "msg": "成功",
    "toUrl": null,
    "jsonData": null
    }

## 八、网站管理--推荐管理--首页
### 请求接口：/api/staffs/staffRecommend
### 请求方式：get/post
### 请求参数：无
### 响应案例：
               {
    "success": true,
    "code": 0,
    "msg": "成功",
    "toUrl": null,
    "jsonData": [
        {
            "educarion": "大专(12)",
            "serviceType": "月嫂",
            "workStatus": "待聘",
            "baseInfo": "26/属狗/福建人",
            "recommendId": 2,
            "headImage": "/images/staff/headImage/201706121108563565304.jpg",
            "staffName": "谢辉",
            "expectedSary": 1242,
            "staffId": 47
        },
        {
            "educarion": "高中",
            "serviceType": "育儿嫂",
            "workStatus": "待聘",
            "baseInfo": "28/属马/上海人",
            "recommendId": 1,
            "headImage": "/images/staff/headImage/201708091137195903757.jpg",
            "staffName": "钱晶",
            "expectedSary": 21324,
            "staffId": 105
            }
        ]
    }
## 九、推荐管理-更换列表(上一个版本接口)
### 请求接口：api/staffs/staffsInfo
### 请求方式：get/post
### 请求参数：无
### 响应案例：
               {
    "success": true,
    "code": 0,
    "msg": "成功",
    "toUrl": null,
    "jsonData": {
        "page": 1,
        "rowNum": 10,
        "records": 14,
        "total": 2,
        "orderBy": null,
        "order": null,
        "rows": [
            {
                "educarion": "中专",
                "serviceType": "月嫂",
                "workStatus": "待聘",
                "baseInfo": "29/属蛇/上海人",
                "headImage": "/images/staff/headImage/201707051832192245773.jpg",
                "staffName": "吴小龙",
                "expectedSary": 1242,
                "staffId": 73
            },
            {
                "educarion": "初中",
                "serviceType": "钟点工",
                "workStatus": "待聘",
                "baseInfo": "25/属猪/江西人",
                "headImage": "/images/staff/headImage/201705041700224104475.jpg",
                "staffName": "康信礼",
                "expectedSary": 12424,
                "staffId": 46
            },
            {
                "educarion": "本科(历史)",
                "serviceType": "家庭管家",
                "workStatus": "待聘",
                "baseInfo": "30/属兔/上海人",
                "headImage": "/images/staff/headImage/201705021656431301903.jpg",
                "staffName": "刘驰明",
                "expectedSary": 20000,
                "staffId": 42
            },
            {
                "educarion": "初中",
                "serviceType": "育儿嫂",
                "workStatus": "待聘",
                "baseInfo": "26/属兔/海南人",
                "headImage": "/images/staff/headImage/201703220003.jpg",
                "staffName": "alin",
                "expectedSary": 8000,
                "staffId": 3
            },
            {
                "educarion": "本科以上(母婴护理)",
                "serviceType": "月嫂,育儿嫂",
                "workStatus": "待聘",
                "baseInfo": "41/属龙/江苏人",
                "headImage": "/images/staff/headImage/320723197611174843.jpg",
                "staffName": "孙千女",
                "expectedSary": 8000,
                "staffId": 40
            },
            {
                "educarion": "大专(教育学)",
                "serviceType": "老人陪护",
                "workStatus": "待聘",
                "baseInfo": "21/属猪/山东人",
                "headImage": "/images/staff/headImage/372925199601028010.jpg",
                "staffName": "赵利壮",
                "expectedSary": 1234,
                "staffId": 33
            },
            {
                "educarion": "初中",
                "serviceType": "保姆,育儿嫂",
                "workStatus": "待聘",
                "baseInfo": "27/属马/河南人",
                "headImage": "/images/staff/headImage/411081199006124597.jpg",
                "staffName": "李清波",
                "expectedSary": 1234,
                "staffId": 32
            },
            {
                "educarion": "本科(育婴师)",
                "serviceType": "保姆",
                "workStatus": "待聘",
                "baseInfo": "24/属鸡/江苏人",
                "headImage": "/images/staff/headImage/321283199306301817.jpg",
                "staffName": "钱飞翔",
                "expectedSary": 123,
                "staffId": 30
            },
            {
                "educarion": "高中",
                "serviceType": "老人陪护,保姆",
                "workStatus": "待聘",
                "baseInfo": "26/属羊/江苏人",
                "headImage": "/images/staff/headImage/321182199109050519.jpg",
                "staffName": "朱征远",
                "expectedSary": 80000,
                "staffId": 28
            },
            {
                "educarion": "中专(营养学)",
                "serviceType": "育儿嫂",
                "workStatus": "待聘",
                "baseInfo": "66/属虎/海南人",
                "headImage": "/images/staff/headImage/201703220005.jpg",
                "staffName": "aaa",
                "expectedSary": 8000,
                "staffId": 5
            }
        ],
        "first": 1,
        "offset": 0,
        "firstPage": true,
        "hasPrePage": false,
        "prePage": 1,
        "pageNos": [
            1,
            2
        ],
        "nextPage": 2,
        "hasNextPage": true,
        "orderBySetted": false,
        "lastPage": false
        }
    }
## 十、微信二维码上传
### 接口名称：/api/website/uploadImg
### 请求方式：get/post
### 请求参数：
请求参数 | 类型|必填|备注
---|---|---|---
qRcode | String |必填|图片服务器路径
### 响应案例：
                {
    "success": true,
    "code": 0,
    "msg": "成功",
    "toUrl": null,
    "jsonData": 0
    }

## 十一、关于我们--图片上传
### 请求路径：/api/website/aboutUsImgPath
### 请求方式：get/post
### 请求参数：file（类型为文件，单张上传）
### 响应案例：
                {
                success: true,
                code: 0,
                msg: "成功",
                toUrl: null,
                jsonData: {
                success: true,
                code: 0,
                msg: "成功",
                toUrl: null,
                jsonData: "http://test.jiacersxy.com:8000/website/aboutus/20170919152522279.jpeg"
                    }
    }        

## 十二、网站管理--推荐管理--获取单条家政人员信息(上一个版本接口)
### 请求路径：/api/staffs/staffInfo
### 请求方式：get/post
### 请求参数： 
请求参数 | 类型|必填|备注|
---|---|---|---
staffId | Integer|必填|员工ID
### 响应案例：
                {
    "success": true,
    "code": 0,
    "msg": "成功",
    "toUrl": null,
    "jsonData": {
        "educarion": "大专(12)",
        "serviceType": "月嫂",
        "workStatus": "待聘",
        "baseInfo": "26/属狗/福建人",
        "recommendId": 2,
        "headImage": "/staff/headImage/201706121108563565304.jpg",
        "staffName": "谢辉",
        "expectedSalary": 1242,
        "staffId": 47
        }
    }
## 十三、网站管理--推荐管理--发布并保存(上一个版本接口)
### 请求路径：/api/staffs/updateStaffRecommend
### 请求方式：get/post
### 请求参数：
RecommendForm对象：
请求参数|类型|必填|备注
---|---|---|---|
id| Integer|必填|对应的是recommendId|
tenantId|Integer|非必填|租户ID
staffId|Integer|必填|阿姨ID
### 响应案例：
                {
    "success": true,
    "code": 0,
    "msg": "成功",
    "toUrl": null,
    "jsonData": 1
    }














