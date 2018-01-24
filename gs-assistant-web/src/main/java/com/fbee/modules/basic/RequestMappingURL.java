package com.fbee.modules.basic;

/**
 * 系统请求URL路径
 * 
 * @ClassName: RequestMappingURL
 * @Description: TODO
 * @author 贺章鹏
 * @date 2016年12月9日 下午3:19:40
 *
 */
public interface RequestMappingURL {
	
	
	/*********************************
	 * 基础接口：用户、我
	 */
	public static final String USERS_BASE_URL = "/api/user";// 用户接口基础地址
	public static final String USERS_INFO_URL = "/api/userInfo";// 用户接口基础地址
	public static final String COMMON_BASE_URL = "/api/common";// 公用接口基础地址
	public static final String SYSTEM_BASE_URL = "/api/system";// 系统管理
	public static final String ORDER_BASE_URL = "/api/orders";// 订单管理
	public static final String STAFF_BASE_URL = "/api/staffs";// 员工管理
	public static final String WEBSITE_BASE_URL = "/api/website";// 网站管理
	public static final String CUSTOMER_BASE_URL = "/api/customers";// 客户管理
	public static final String FUNDS_BASE_URL = "/api/funds";// 财务管理
	public static final String INDEX_BASE_URL = "/api/index";// 首页管理
	public static final String BANNER_BASE_URL = "/api/banner";// banner接口
	public static final String CACHE_BASE_URL = "/api/cache";// cache接口
	public static final String JOB_BASE_URL = "/api/job";// job接口
	
	public static final String ORDER_SHARE_BASE_URL = "/api/orderShare"; //淘蜂享
	
	public static final String BANK_CODE = "/getBank";	//获取银行代码
	

	/********************************* 子级接口  *****************************************/

	public static final String LOGIN_URL = "/login";// 登陆接口
	public static final String LOGOUT_URL = "/logout"; // 退出
	public static final String CONTROLLOGIN_URL = "/controlLogin"; // 退出
	public static final String CAPTCHA_URL = "/getCaptcha";// 图形验证码
	public static final String MSG_CAPTCHA_URL = "/getMsgCaptcha";// 短信验证码
	public static final String SENDSMS = "/sendSms";//短信验证码
	public static final String MAINPAGE_DATA = "/mainPageData";//主页数据
	
	public static final String USER_MENUS_URL = "/userMenus";// 用户菜单
	public static final String INFO_URL = "/info";// 详细信息
	public static final String MODIFY_URL = "/modify";// 详细信息
	public static final String STAFFRECOMMEND_URL = "/staffRecommend";// 推荐阿姨信息
	public static final String UPDATESTAFFRECOMMEND_URL = "/updateStaffRecommend";// 更换推荐阿姨
	public static final String SAVESTAFFRECOMMEND_URL = "/saveStaffRecommend";//新增推荐阿姨列表
	public static final String STAFFSINFO_URL = "/staffsInfo";// 所有阿姨信息
	public static final String STAFFINFO_URL = "/staffInfo";// 每个阿姨信息
	public static final String STAFFSINFOLIKE_URL = "/staffsInfoLike";// 模糊查询阿姨列表
	public static final String BANNER_UPLOAD_URL = "/upload"; // Banner图片上传
	public static final String BANNER_UPDATE = "/save"; // Banner设置保存
	public static final String BANNER_INFO = "/getBannerInfo"; // Banner信息获取
	public static final String BANNER_SYSTEM_LIST = "/getSystemBanners"; // Banner信息获取
	public static final String SERITEMS_URL = "/serviceItems"; // 服务工种列表
	public static final String SERITEM_URL = "/serviceItem"; // 服务工种
	public static final String GETQRCODE_URL = "/getQrCode";// 获取租户二维码
	public static final String PUBLISH_SAVE_RECOMMEND_MANAGE_URL="/saveRecommendStaff";//保存推荐管理人员信息
	public static final String GET_RESUM_DETAIL_TWODIMENSION_CODE_URL="/getResumDetailTwoDimensionCode";//获取简历箱阿姨详情
	public static final String	Withdrawals_BASE_URL="/api/withdrawals";//立即提现
	
	public static final String 	Withdrawals_URL = "/withdrawals"; //提现提交

	public static final String USER_PERMISSION_URL = "/getUserPermission";	//获取用户权限
	public static final String USER_SYS_SETTING_URL = "/getUserSysSetting";	//获取用户系统设置
	public static final String TENANTS_USER_LISTINFO = "/getTenatsUserList";//获取门店用户列表 - 系统管理-账号管理
	public static final String TENANTS_USER_LISTINFO_SEL = "/getTenatsUserListSel";//获取门店用户列表下拉框
	public static final String TENANTS_USER_DELETE = "/deleteTenatsUser";	//删除门店用户
	public static final String TENANTS_USER_ADD = "/addTenatsUser";			//添加门店用户
	public static final String TENANTS_USER_UPDATE = "/updateTenatsUser";	//修改门店用户
	public static final String TENANTS_USER_INFO = "/getTenatsUserInfo";	//获取门店用户
	public static final String TENANTS_USER_RESET_PASSWORD = "/resetPassword";	//重置密码
	public static final String TENANTS_USER_ADD_CHECK = "/sysUser/check/send";// 添加用户时，获取短信验证码

	/********************************* 员工管理模块 *****************************************/
	public static final String BRUSH_CERT_INFO = "/brushCard";// 刷身份证操作
	public static final String 	GET_CERT_INFO="getCertInfo";//根据身份证获取阿姨信息
	public static final String SAVE_STAFF_BASE_INFO = "/add/baseInfo";// 保存阿姨基础信息
	public static final String SAVE_STAFF_JOB_INFO = "/add/jobInfo";// 保存阿姨求职信息
	public static final String SAVE_ITEMS_INFO = "/add/saveItemsInfo";// 保存工种信息
	public static final String DEL_STAFF_ITEMS_INFO = "/del/delStaffItemsInfo";// 删除工种

	public static final String SAVE_STAFF_MEDIA_INFO = "/add/mediaInfo";// 保存阿姨视频图片信息
	public static final String SAVE_STAFF_BANK_INFO = "/add/bankInfo";// 保存阿姨银行信息
	public static final String SAVE_STAFF_POLICY_INFO = "/add/policyInfo";// 保存阿姨信息
	public static final String SAVE_STAFF_CERTS_INFO = "/add/certInfo";// 保存阿姨服务认证信息
	public static final String QUERY_STAFF = "/queryStaff";// 查询
	public static final String GET_STAFF_DETAILS = "/getDetails";// 查询
	public static final String GET_STAFF_DETAILS_PART_BASE = "/getDetails/part/base";// 查询基础信息
	public static final String GET_STAFF_DETAILS_PART_MEDIA = "/getDetails/part/media";// 查询图片／视频信息
	public static final String GET_STAFF_DETAILS_PART_FINANCE = "/getDetails/part/finance";// 查询财务信息
	public static final String GET_STAFF_DETAILS_PART_RECORD = "/getDetails/part/record";// 查询工作记录信息
	public static final String STAFF_IMGAE_DEFAULT = "/add/imageDefault";// 设置图片默认
	public static final String STAFF_ON_OFF_SHELF = "/add/onOffShelf";// 保存阿姨上下架
	public static final String STAFF_FINANCIAL_RECORD = "/add/financialRecord";// 保存阿姨财务记录
	public static final String GET_SESSIONSTAFF_ID = "/getSessionStaffId";// 获取session中的新增阿姨ID
	public static final String STAFF_WORK_LIST = "/staffWorkList";// 阿姨工作安排表
	public static final String STAFF_RECOMMEND_LIST="/staffRecommend";//推荐阿姨
	public static final String LOGIC_DELETE_CERT="/logicDeleteCert";//逻辑删除不通过的证书
	public static final String CERT_VALID="/certValid";//证书校验
	public static final String GET_STAFF_BANKINFO = "/getStaffBankInfo";// 查询家政员银行卡信息
	public static final String GET_STAFF_POLICYLIST = "/getPolicyList";// 查询家政员保单信息
	public static final String GET_STAFF_FINANCEINFO = "/getFinanceInfo";// 查询家政员财务记录
	public static final String GET_STAFF_WORKLIST = "/getWorkList";// 查询家政员派工记录

	/********************************** 网站管理模块 *****************************************/
	public static final String WEBSITE_GET_INDEX_INFO = "/getIndexInfo";// 获取网站管理-首页管理信息
	public static final String WEBSITE_GET_CONTACT_INFO = "/getContactInfo";// 获取网站管理-联系方式信息
	public static final String WEBSITE_UPDATE_CONTACT_INFO = "/updateContactInfo";// 网站管理-更新联系方式信息
	public static final String WEBSITE_UPLOAD_IMG = "/uploadImg";// 网站管理-更新联系方式信息
	public static final String UPDATESERITEM_URL = "/updateServiceItem"; // 服务工种
	public static final String UPLOADBANNERIMG_URL = "/uploadBannerImg";// 获取网站管理-首页管理信息
	public static final String UPDATESERITEMS_URL = "/updateServiceItems";// 获取网站管理-首页管理信息

	/* 招聘管理 */
	public static final String TENANTS_JOBS_INDEX = "/tenantsJobsIndex"; // 招聘管理首页请求URL
	public static final String GET_TENANTS_JOBS_LIST_INFO = "/getTenantsJobsListInfo"; // 查询-招聘信息列表
	public static final String SAVE_TENANTS_JOBS_INFO = "/saveTenantsJobsInfo"; // 保存招聘信息
	public static final String UPDATE_TENANTS_JOBS_INFO = "/updateTenantsJobsInfo"; // 更新招聘信息
	public static final String GET_TENANTS_JOBS_INFO_BY_ID = "/getTenantsJobsDetail"; // 招聘信息回显页面
	public static final String GET_TENANTS_JOBS_MTIME_USABLE = "/updateTenantsJobsMTimeAndIsUsable"; // 刷新 修改 时间 或修改上下架
	
	public static final String GET_SERVICE_TYPE = "/getServiceType"; // 获取服务工种下拉列表
	public static final String GET_AGE_RANGE = "/getAgerange"; // 获取年龄范围下拉列表
	public static final String GET_SALARY_RANGE = "/getSalaryRange"; // 获取薪资范围下拉列表
	public static final String GET_SERVICE_MOLD = "/getServiceMold"; // 获取服务类型下拉列表
	//public static final String GET_SERVICE_TYPE = "/getServiceType"; // 获取工种类型下拉列表
	/* 关于我们 */
	public static final String ABOUT_US_INDEX = "/aboutUsIndex"; // 关于我们首页请求URL
	public static final String GET_ABOUT_US_INFO = "/queryAboutUsInfo"; // 查询-页面信息填充初始化
	public static final String SAVE_ABOUT_US_INFO = "/saveAboutUsInfo"; // 保存-页面基本信息保存
	public static final String DELETE_ABOUT_US_INFO = "/deleteAboutUsInfo"; // 删除-文件信息上载更新索引
	public static final String ABOUT_US_IMG_PATH="/aboutUsImgPath";//关于我们图片上传

	/* 推荐管理*/
	public static final String RECOMMEND_MANAGEMENT ="/recommendManagement"; //网站管理-推荐管理
	public static final String UPDATE_RECOMMEND_MANAGERMENT="/updateRecommendManageMent";//网站管理-修改推荐管理人员信息

	/*服务管理*/
	public static final String SERVICE_MANAGE_DETAILS="/getServiceManageDetails";//网站管理-服务管理首页
	public static final String ROOT_SERVICE_DETAILS="/getRootServiceDetails";//网站管理-服务管理首页
	public static final String UPDATE_SERVICE_MANAGE_DETAILS="/updateServiceManageDetalisInfo";//服务管理-更新信息

	/********************************* 财务管理模块 *****************************************/
	public static final String GET_TENANTS_FUNDS_INFO = "/getTenantsFundsInfo"; // 查询租户账户信息
	public static final String GET_TENANTS_FORTUNE_INFO = "/getTenantsFortuneInfo"; // 查询租户财务总信息
	public static final String GET_TENANTS_FINANCE_LIST_INFO = "/getTenantsFinanceListInfo";// 查询租户财务流水信息
	
	/*********************************订单管理模块*****************************************/
	public static final String	ORDERSLIST_URL = "/ordersList";//订单管理-订单信息列表获取
	public static final String	ORDERINFO_URL = "/orderInfo";//订单管理-订单详情
	public static final String	ORDERMEMBERINFO_URL = "/orderMemberInfo";//订单管理-客户详情
	public static final String	ORDERSERVICEINFO_URL = "/orderServiceInfo";//订单管理-服务详情
	public static final String	ORDERPAYINFO_URL = "/orderPayInfo";//订单管理-支付详情
	public static final String	ORDERCONTRACTINFO_URL = "/orderContractInfo";//订单管理-合同详情
	public static final String	ORDERSHAREINFO_URL = "/orderShareInfo";//订单管理-分享详情
	public static final String	ORDERTAOSHAREINFO_URL = "/orderTaoShareInfo";//订单管理-淘分享详情
	public static final String	PASSINTERVIEW_URL = "/passInterview";//订单管理-面试通过
	public static final String	SAVEORDER_URL = "/saveOrder";//订单管理-保存订单详情信息
	public static final String	CHANGESTAFF_URL = "/changeStaff";//订单管理-更换阿姨
	public static final String	ORDERSHARE_URL = "/orderShare";//订单管理-订单分享
	public static final String	SAVECONTRACTINFO_URL = "/saveContractInfo";//订单管理-保存合同信息(合同图片上传)
	public static final String	DELETECONTRACTINFO_URL = "/deleteContractInfo";//订单管理-删除合同信息
	public static final String	CREATEORDER_URL = "/createOrder";//订单管理-创建订单
	public static final String	SAVECUSTOMERINFO_URL = "/saveCustomerInfo";//订单管理-保存客户信息
	public static final String	SAVESERVICEINFO_URL = "/saveServiceInfo";//订单管理-保存服务信息
	public static final String	ORDERSTAFFINFO_URL = "/orderStaffInfo";//订单管理-阿姨详情
	public static final String	PAYDEPOSIT_URL = "/orderPayDeposit";//订单管理-本地订单支付定金
	public static final String	PAYBALANCE_URL = "/orderPayBalance";//订单管理-本地订单支付尾款
	public static final String	GETSTAFFLIST_URL = "/getStaffList";//订单管理-获取阿姨列表
	public static final String	GETCREATESTAFFLIST_URL = "/getCreateStaffList";//订单管理-获取创建订单阿姨列表
	public static final String	ORDERCHANGESTAFF_URL = "/orderChangeStaff";//订单管理-结单后更换阿姨
	public static final String	DELETECONTRACR_URL = "/deleteContract";//删除合同(单个)
	public static final String UPDATE_ORDERS_CUSTOMERS_INFO = "/updateOrdersCustomersInfo";//更新订单客户信息
	public static final String	DEPOSITPAYMENT_URL = "/depositPayment";//本地订单-本地订单-定金支付
	public static final String	MODIFYDEPOSIT_URL = "/modifyDeposit";//修改定金
	public static final String	CHANGEHS_URL = "/changehs";//本地订单-更换阿姨

	/********************************* 系统管理模块 *****************************************/
	public static final String GET_USER_TELEPHONE = "/getUserTelephone";// 查询用户绑定手机号
	public static final String UPDATE_USER_PASSWORD_INFO = "/updateUserPasswordInfo";// 更改密码


	/********************************* 客户管理模块 *****************************************/

	/********************************* 客户管理模块--bannner *******************************/

	public static final String CUSTOMER_QUERY_URL = "/querycustomers";// 用户列表
	public static final String CUSTOMER_GET_URL = "/getcustomer";// 用户详情
	public static final String CUSTOMER_SAVE_URL = "/savecustomer";// 用户新增


	/********************************* 缓存管理模块 *****************************************/
	public static final String INIT_CACHE_DATA = "/initCache";// 初始化系统缓存数据
	
	public static final String CLEAR_CACHE_DATA="/clearCache";//清除缓存

	/********************************* 页面字典参数和下拉框模块 *****************************/
	public static final String GET_DICTIONARY_DATA = "/getDictionaryData/{typeCode}";// 获取对应的字典参数参数List
	public static final String GET_AREA_DATA = "/getAreaData/{typeCode}";// 获取区域参数list
	public static final String GET_SKILLS_DATA = "/getSKillsData/{typeCode}";// 获取技能特点（对应服务工种）、个人特点、服务类型
	public static final String CUSTOMER_UPDATE_URL = "/updatecustomer";// 用户修改
	public static final String ORDERCUSTOMER_UPDATE_URL = "/orderCustomer";// 用户修改
	public static final String CUSTOMER_HOMEINFO_UPDATE_URL = "/updateCustomerHomeInfo";//修改用户家庭信息
	
	public static final String GET_TWODIMENSION_CODE_URL = "/getTwoDimensionCode";// 获取二维码
/*********************************预约订单管理模块*****************************************/

	public static final String GET_RESERVE_ORDERS_COUNT = "/getReserveOrdersCount";//查询预约订单列表
	public static final String GET_RESERVE_ORDERS_URL = "/getReserveOrdersList";//查询预约订单列表
	
	public static final String GET_RESERVE_URL = "/getReserve";//查询预约订单-预约信息

	public static final String GET_RESERVE_MEMBER_URL = "/getReserveMember";//查询预约订单-客户信息

	public static final String GET_RESERVE_SERVICE_URL = "/getReserveService";//查询预约订单-服务信息
	
	public static final String GET_RESERVE_STAFF_URL = "/getReserveStaff";//查询预约订单-阿姨信息

	public static final String SAVE_RESERVE_MEMBER_URL = "/saveReserveMember";//预约订单-保存客户信息至客户管理
	
	public static final String UPDATE_MEMBER_URL = "/saveMemberToReserve";//预约订单-保存客户信息至订单

	public static final String UPDATE_SERVICE_URL = "/saveServiceToReserve";//预约订单-保存服务信息至订单
	public static final String UPDATE_RESERVE_REMARK_URL = "/remark/modify";//预约订单-保存服务信息至订单

	public static final String UPDATE_RESERVE_URL = "/updateReserve";//预约订单-完成处理
	
	public static final String SAVE_RESERVE_URL = "/saveReserve";//预约订单-生成订单
	
	public static final String GET_RESERVE_STAFF_LIST = "/getReserveStaffList";//预约订单-获取阿姨信息列表

	public static final String UPDATE_STAFF_URL = "/updateStaff";//预约订单-更换阿姨

	public static final String  CANCELORDERSTARUS_URL="cancelOrderStatus";//当状态为：01待支付定金，02待面试，03待支付尾款-取消订单
	/********************************* 淘蜂享管理模块 *****************************************/
	//蜂享池
	public static final String GET_ORDER_SHARE_INFO_LIST = "/getOrderShareInfoList"; //查询蜂享池信息列表
	public static final String GET_ORDER_DETAIL_INFO =  "/getOrderDetailInfo";//查看订单详情
	public static final String GET_STAFFS_INFO = "/getStaffsInfo";//获取阿姨列表信息
	public static final String SUBMIT_STAFFS_INFO = "/submitStaffInfo";//选中阿姨确认提交
	//投递箱
	public static final String GET_DELIVERY_BOX_INFO_LIST = "/getDeliveryBoxInfoList";//获取投递箱信息列表
	public static final String GET_STAFF_DETAIL_INFO = "/getStaffDetailInfo";//投递箱获取阿姨明细信息
	public static final String STAFF_PASS = "/staffPass";//阿姨通过
	public static final String STAFF_REJECT = "/rejectStaff";//阿姨拒绝
	public static final String STAFF_RETURN = "/staffReturn";//阿姨退回
	public static final String STAFF_INTERVIEW_PASS = "/staffInterviewPass";
	
	public static final String SET_COVER = "/setCover";//设置封面
	public static final String DELETE_MEDIA_PATH = "/deleteMediaPath";//删除图片或视频
	public static final String UPLOAD_MEDIA_PATH = "/uploadMediaPath";//上传视频秀
	public static final String SET_DEFAULT_COVER = "/setDefaultCover";//设置默认封面

	public static final String WECHAT_BASE_URL = "/api/WechatInfo";
	public static final String GET_WECHAT_INFO = "/info";
	public static final String WECHAT_AUTHRIZE = "/authrize";
	public static final String PROCESS_URL = "/process"; // 微信验证

}
