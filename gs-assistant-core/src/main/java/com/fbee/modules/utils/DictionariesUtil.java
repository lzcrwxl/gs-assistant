package com.fbee.modules.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fbee.modules.core.utils.StringUtils;
import com.fbee.modules.utils.ControlEntity;


public class DictionariesUtil {

	/***
	 * 设置年龄范围 下拉框值
	 * @return
	 */
	public static List<ControlEntity> setAgerange(){
		List<ControlEntity> list = new ArrayList<ControlEntity>();
		list.add(new ControlEntity ("00", "不限"));
		list.add(new ControlEntity ("01", "29岁以下"));
		list.add(new ControlEntity ("02", "29-39岁"));
		list.add(new ControlEntity ("03", "39-49岁"));
		list.add(new ControlEntity ("04", "50岁以上"));
		return list;
	}
	/**
	 * 获取年龄范围 返显
	 * @return
	 */
	public static String getAgerange(String key){
		if(StringUtils.isBlank(key)){
			return "";
		}
		Map map = new HashMap();
		map.put("00", "不限");
		map.put("01", "29岁以下");
		map.put("02", "29-39岁");
		map.put("03", "39-49岁");
		map.put("04", "50岁以上");
		return  map.get(key).toString();
	}

	/***
	 * 设置工种下拉框值
	 * @return
	 */
	public static List<ControlEntity> setServiceType(){
		List<ControlEntity> list = new ArrayList<ControlEntity>();
		list.add(new ControlEntity ("01", "月嫂"));
		list.add(new ControlEntity ("02", "育儿嫂"));
		list.add(new ControlEntity ("03", "保姆"));
		list.add(new ControlEntity ("04", "老人陪护"));
		list.add(new ControlEntity ("05", "钟点工"));
		list.add(new ControlEntity ("06", "家庭管家"));
		list.add(new ControlEntity ("07", "医院护工"));
		return list;
	}
	/**
	 * 获取工种 返显
	 * @return
	 */
	public static String getServiceType(String key){
		Map map = new HashMap();
		map.put("01", "月嫂");
		map.put("02", "育儿嫂");
		map.put("03", "保姆");
		map.put("04", "老人陪护");
		map.put("05", "钟点工");
		map.put("06", "家庭管家");
		map.put("07", "医院护工");
		return  map.get(key).toString();
	}


	/***
	 * 设置薪资范围 下拉框值
	 * @return
	 */
	public static List<ControlEntity> setSalaryRange(){
		List<ControlEntity> list = new ArrayList<ControlEntity>();
		list.add(new ControlEntity ("01", "元/月"));
		list.add(new ControlEntity ("03", "元/26天"));
		list.add(new ControlEntity ("02", "元/小时"));
		list.add(new ControlEntity ("04", "元/天"));
		return list;
	}
	/**
	 * 获取薪资范围 返显
	 * @return
	 */
	public static String getSalaryRange(String key){
		Map map = new HashMap();
		map.put("01", "元/月");
		map.put("02", "元/小时");
		map.put("03", "元/26天");
		map.put("04", "元/天");
		return  map.get(key).toString();
	}

	/***
	 * 设置服务类型 下拉框值
	 * @param key 服务工种 value
	 * @return
	 */
	public static List<ControlEntity> setServiceMold(String key){
		List<ControlEntity> list = new ArrayList<ControlEntity>();

		if(key.equals("01") || key.equals("02")|| key.equals("06")){
			list.add(new ControlEntity ("01", "住家"));
		}

		if(key.equals("03") || key.equals("04")){
			list.add(new ControlEntity ("01", "住家"));
			list.add(new ControlEntity ("02", "不住家"));
		}
		if(key.equals("05")){
			list.add(new ControlEntity ("03", "临时"));
			list.add(new ControlEntity ("04", "长期"));
		}
		if(key.equals("07")){
			list.add(new ControlEntity ("05", "24小时"));
			list.add(new ControlEntity ("06", "白班"));
			list.add(new ControlEntity ("07", "夜班"));
		}
		return list;
	}

	/***
	 * 获取服务类型 返显
	 * @param key
	 * @return
	 */
	public static String getServiceMold(String key){
		Map map = new HashMap();
		map.put("01", "住家");
		map.put("02", "不住家");
		map.put("03", "临时");
		map.put("04", "长期");
		map.put("05", "24小时");
		map.put("06", "白班");
		map.put("07", "夜班");
		return  map.get(key).toString();
	}


	/***
	 * 获取加个区间返显
	 * @param key
	 * @return
	 */
	public static String getPriceRangeValue(String key){
		Map map = new HashMap();
		map.put("01", "4000元以下");
		map.put("02", "4000-6999元");
		map.put("03", "7000-9999元");
		map.put("04", "10000-14999元");
		map.put("05", "15000元以上");
		return  map.get(key).toString();
	}





	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(DictionariesUtil.getAgerange("01"));
	}

	//从业经验
    public static String getExperienceValue(String experience) {
		switch (experience){
			case "01": return "1年以下";
			case "02": return "1-2年";
			case "03": return "3-5年";
			case "04": return "5年以上";
			default: return "";
		}
    }



	public static List<ControlEntity> getServiceIncomeList(){
		List<ControlEntity> list = new ArrayList<ControlEntity>();

		List<ControlEntity> mList = new ArrayList<ControlEntity>();//月
		mList.add(new ControlEntity ("01", "4000元以下"));
		mList.add(new ControlEntity ("02", "4000-6999元"));
		mList.add(new ControlEntity ("03", "7000-9999元"));
		mList.add(new ControlEntity ("04", "10000-14999元"));
		mList.add(new ControlEntity ("05", "15000元以上"));

		List<ControlEntity> sList = new ArrayList<ControlEntity>();//时
		sList.add(new ControlEntity ("01", "30元以下"));
		sList.add(new ControlEntity ("02", "30-49元"));
		sList.add(new ControlEntity ("03", "50-69元"));
		sList.add(new ControlEntity ("04", "70-99元"));
		sList.add(new ControlEntity ("05", "100元以上"));

		List<ControlEntity> tList = new ArrayList<ControlEntity>();//天
		tList.add(new ControlEntity ("01", "150元以下"));
		tList.add(new ControlEntity ("02", "150-249元"));
		tList.add(new ControlEntity ("03", "250-349元以下"));
		tList.add(new ControlEntity ("04", "350-499元以下"));
		tList.add(new ControlEntity ("05", "500元以上"));

		list.add(new ControlEntity ("01", "按月",mList));
		list.add(new ControlEntity ("02", "按天",sList));
		list.add(new ControlEntity ("03", "按小时",tList));

		return list;
	}

}
