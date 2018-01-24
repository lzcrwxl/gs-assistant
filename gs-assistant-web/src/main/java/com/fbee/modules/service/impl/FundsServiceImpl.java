package com.fbee.modules.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbee.modules.bean.ErrorMsg;
import com.fbee.modules.core.Log;
import com.fbee.modules.core.page.Page;
import com.fbee.modules.form.TenantsFinanceRecordsForm;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.jsonData.basic.ResultCode;
import com.fbee.modules.mybatis.dao.TenantsFinanceRecordsMapper;
import com.fbee.modules.mybatis.dao.TenantsFundsMapper;
import com.fbee.modules.mybatis.entity.TenantsFinanceRecordsEntity;
import com.fbee.modules.mybatis.model.TenantsFinanceRecords;
import com.fbee.modules.mybatis.model.TenantsFinanceRecordsExample;
import com.fbee.modules.service.FundsService;
import com.fbee.modules.service.basic.BaseService;
import com.google.common.collect.Maps;

@Service
public class FundsServiceImpl extends BaseService implements FundsService {

	@Autowired
	TenantsFundsMapper tenantsFundsDao;
	@Autowired
	TenantsFinanceRecordsMapper tenantsFinanceRecordsDao;

	@Override
	public JsonResult getByTenantsId(int id) {
		return JsonResult.success(tenantsFundsDao.getByTenantsId(id));
	}

	@Override
	public JsonResult getFinanceTotalByTenantsId(int id) {
		// 查询财务总览信息
		try{
		
			BigDecimal expenseMoney = new BigDecimal(0);
			BigDecimal incomeMoney = new BigDecimal(0);
			TenantsFinanceRecordsExample example = new TenantsFinanceRecordsExample();
			example.createCriteria().andTenantIdEqualTo(id).andInOutTypeEqualTo("01").andStatusEqualTo("03");
			List<TenantsFinanceRecords> tenantsFinanceRecords = tenantsFinanceRecordsDao.selectByExample(example );// 获取总收入
			if(tenantsFinanceRecords.size()>0){
				for (TenantsFinanceRecords tenantsFinanceRecords2 : tenantsFinanceRecords) {
					BigDecimal inOutAmount = tenantsFinanceRecords2.getInOutAmount();
					if(null == inOutAmount)continue;
					incomeMoney = incomeMoney.add(inOutAmount);
				}
			}
			
			TenantsFinanceRecordsExample example1 = new TenantsFinanceRecordsExample();
			example1.createCriteria().andTenantIdEqualTo(id).andInOutTypeEqualTo("02").andStatusEqualTo("03");
			List<TenantsFinanceRecords> tenantsFinanceRecords2 = tenantsFinanceRecordsDao.selectByExample(example1 );// 获取总支出
			if(tenantsFinanceRecords2.size()>0){
				for (TenantsFinanceRecords tenantsFinanceRecords3 : tenantsFinanceRecords2) {
					BigDecimal inOutAmount = tenantsFinanceRecords3.getInOutAmount();
					if(null == inOutAmount)continue;
					expenseMoney = expenseMoney.add(inOutAmount);
				}
			}
			
			BigDecimal totalMoney = incomeMoney.subtract(expenseMoney);// 总利润
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("totalMoney", totalMoney);
			map.put("incomeMoney", incomeMoney);
			map.put("expenseMoney", expenseMoney);
			return JsonResult.success(map);
		}catch (Exception e) {
			e.printStackTrace();
			Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
			return JsonResult.failure(ResultCode.DATA_ERROR);
		}
	}

	@Override
	public JsonResult getFinanceByTenantsId(TenantsFinanceRecordsForm tenantsFinanceRecordsForm, int pageNumber,
			int pageSize) {
		// 查询财务流水信息
		try {
			TenantsFinanceRecordsEntity tenantsFinanceRecordsEntity = new TenantsFinanceRecordsEntity();
			tenantsFinanceRecordsEntity.setStartTime(tenantsFinanceRecordsForm.getStartTime());// 查询流水时的开始时间
			tenantsFinanceRecordsEntity.setEndTime(tenantsFinanceRecordsForm.getEndTime());// 查询流水时的结束时间
			tenantsFinanceRecordsEntity.setInOutType(tenantsFinanceRecordsForm.getInOutType());// 收支类型
			tenantsFinanceRecordsEntity.setTenantId(tenantsFinanceRecordsForm.getTenantsId());// 租户id
			tenantsFinanceRecordsEntity.setInOutNo(tenantsFinanceRecordsForm.getInOutNo());// 查询流水单号
			tenantsFinanceRecordsEntity.setDraweeType(tenantsFinanceRecordsForm.getDraweeType());//身份类别
			Integer totalCount = tenantsFinanceRecordsDao.getCountById(tenantsFinanceRecordsEntity);
			// 分页实体
			Page<Map> page = new Page<Map>();
			Map<String, Object> map = new HashMap<String, Object>();
			page.setPage(pageNumber);
			page.setRowNum(pageSize);
			// 最大页数判断
			int pageM = maxPage(totalCount, page.getRowNum(), page.getPage());
			if (pageM > 0) {
				page.setPage(pageM);
			}
			if (totalCount > 0) {
				
				tenantsFinanceRecordsEntity.setPageNumber(page.getOffset());
				tenantsFinanceRecordsEntity.setPageSize(page.getRowNum());
				List<Map> resultList1 = tenantsFinanceRecordsDao.getByTenantsId(tenantsFinanceRecordsEntity);
				//收入总条数
				Integer inTotalCount = tenantsFinanceRecordsDao.getInCountById(tenantsFinanceRecordsEntity);
				//支出总条数
				Integer outTotalCount = tenantsFinanceRecordsDao.getOutCountById(tenantsFinanceRecordsEntity);
				
				//收入总金额
				BigDecimal inTotalAmount = tenantsFinanceRecordsDao.getInAmountById(tenantsFinanceRecordsEntity);
				//支出总金额
				BigDecimal outTotalAmount = tenantsFinanceRecordsDao.getOutAmountById(tenantsFinanceRecordsEntity);
				
				System.out.println(inTotalCount);
				System.out.println(outTotalCount);
				
				page.setRows(resultList1);
				page.setRecords(totalCount.longValue());
				if ("01".equals(tenantsFinanceRecordsEntity.getInOutType())) {
					map.put("inTotalCount", inTotalCount);
					map.put("outTotalCount", 0);
				}
				if ("02".equals(tenantsFinanceRecordsEntity.getInOutType())) {
					map.put("inTotalCount", 0);
					map.put("outTotalCount", outTotalCount);
				}
				if ("".equals(tenantsFinanceRecordsEntity.getInOutType())||null == tenantsFinanceRecordsEntity.getInOutType()) {
					map.put("inTotalCount", inTotalCount);
					map.put("outTotalCount", outTotalCount);
				}
				
				map.put("inTotalAmount", inTotalAmount);
				map.put("outTotalAmount", outTotalAmount);
				map.put("records", totalCount.longValue());
				map.put("rows", resultList1);
				
			}
			
			return JsonResult.success(map);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(ErrorMsg.STAFF_QUERY_ERR, e);
			return JsonResult.failure(ResultCode.DATA_ERROR);
		}

	}

}
