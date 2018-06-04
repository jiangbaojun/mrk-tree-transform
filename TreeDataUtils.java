package com.vstsoft.framework.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 树形数据格式转换
 * @Author		姜宝俊
 * @Group 		技术组
 * @Worker	 	1861  
 * @date 		2018年6月4日 上午10:19:34  
 * @Company		Vstsoft
 */
public class TreeDataUtils {
	
	private static String T_ID = "id";
	private static String T_PID = "pid";
	private static String TOP_PID_VALUE = "-1";
	private static String T_CHILDRENNAME = "children";
	
	public static void main(String[] args) {
		//FLAT TO TREE
//		List<HashMap<String, Object>> list = getFlatData();
//		System.out.println(list);
//		List<HashMap<String, Object>> result = flatToTree(list,"Id","pid","children");
//		System.out.println(result);
//		
		//LIST TREE TO FLAT
//		List<HashMap<String, Object>> list = getListTreeData();
//		System.out.println(list);
//		List<HashMap<String, Object>> result = treeToFlat(list,"-1","Id","pid","children");
//		System.out.println(result);
		
		//OBJECT TREE TO FLAT
//		HashMap<String, Object> map = getObjectTreeData();
//		System.out.println(map);
//		List<HashMap<String, Object>> result = treeToFlat(map,"-1","Id","pid","children");
//		System.out.println(result);
		
	}
	
	/**
	 * 数组标准树形数据->转换->扁平树形数据。 生成扁平树形数据顶级父节点值，默认-1，标准树形数据-id节点属性名称，默认id，标准树形数据-父id节点属性名称，默认pid，标准树形数据-子节点的属性名称，默认children
	 * @param list	数组标准树形数据
	 * @return		扁平树形数据
	 * @Author		姜宝俊
	 * @Group 		技术组
	 * @Worker	 	1861 
	 * @date 		2018年6月4日 上午10:04:11     
	 * @Company		Vstsoft
	 * @version 	V1.0
	 */
	public static List<HashMap<String, Object>> treeToFlat(List<HashMap<String, Object>> list){
		return treeToFlat(list,TOP_PID_VALUE,T_ID,T_PID,T_CHILDRENNAME);
	}
	
	/**
	 * 对象标准树形数据->转换->扁平树形数据。 生成扁平树形数据顶级父节点值，默认-1，标准树形数据-id节点属性名称，默认id，标准树形数据-父id节点属性名称，默认pid，标准树形数据-子节点的属性名称，默认children
	 * @param map	对象标准树形数据
	 * @return		扁平树形数据
	 * @Author		姜宝俊
	 * @Group 		技术组
	 * @Worker	 	1861 
	 * @date 		2018年6月4日 上午10:07:16     
	 * @Company		Vstsoft
	 * @version 	V1.0
	 */
	public static List<HashMap<String, Object>> treeToFlat(HashMap<String, Object> map){
		return treeToFlat(map,TOP_PID_VALUE,T_ID,T_PID,T_CHILDRENNAME);
	}
	/**
	 * 数组标准树形数据->转换->扁平树形数据
	 * @param list	数组标准树形数据
	 * @param parentId	生成扁平树形数据顶级父节点值
	 * @param id	标准树形数据-id节点属性名称，默认id
	 * @param pid	标准树形数据-父id节点属性名称，默认pid
	 * @param childrenName	标准树形数据-子节点的属性名称，默认children
	 * @return		扁平树形数据
	 * @Author		姜宝俊
	 * @Group 		技术组
	 * @Worker	 	1861 
	 * @date 		2018年6月4日 上午9:55:31     
	 * @Company		Vstsoft
	 * @version 	V1.0
	 */
	public static List<HashMap<String, Object>> treeToFlat(List<HashMap<String, Object>> list, String parentId, String id, String pid, String childrenName){
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		for(int i=0;i<list.size();i++) {
			result.addAll(mapToFlat(list.get(i),parentId,id,pid,childrenName,null));
		}
		return result;
	}
	
	/**
	 * 
	 * 对象标准树形数据->转换->扁平树形数据
	 * @param map	对象标准树形数据
	 * @param parentId	生成扁平树形数据顶级父节点值
	 * @param id	标准树形数据-id节点属性名称，默认id
	 * @param pid	标准树形数据-父id节点属性名称，默认pid
	 * @param childrenName	标准树形数据-子节点的属性名称，默认children
	 * @return		扁平树形数据
	 * @Author		姜宝俊
	 * @Group 		技术组
	 * @Worker	 	1861 
	 * @date 		2018年6月4日 上午9:57:46     
	 * @Company		Vstsoft
	 * @version 	V1.0
	 */
	public static List<HashMap<String, Object>> treeToFlat(HashMap<String, Object> map, String parentId, String id, String pid, String childrenName){
		List<HashMap<String, Object>> result = mapToFlat(map,parentId,id,pid,childrenName,null);
		return result;
	}
	
	/**
	 * 标准树形数据->转换->扁平树形数据 内部方法
	 * @Author		姜宝俊
	 * @Group 		技术组
	 * @Worker	 	1861 
	 * @date 		2018年6月4日 上午9:58:31     
	 * @Company		Vstsoft
	 * @version 	V1.0
	 */
	private static List<HashMap<String, Object>> mapToFlat(HashMap<String, Object> map, String parentId, String id, String pid, String childrenName, List<HashMap<String, Object>> result){
		if(id==null) id = T_ID;
		if(pid==null) pid = T_PID;
		if(childrenName==null) childrenName = T_CHILDRENNAME;
		if(result==null) result = new ArrayList<HashMap<String, Object>>();
		
		HashMap<String, Object> node = new HashMap<String, Object>();
		
		//循环map，把map放入node中
		for(Iterator<Entry<String, Object>> iteator=map.entrySet().iterator();iteator.hasNext();){
			Entry<String, Object> entry=iteator.next();
			String key = entry.getKey();
			if(key!=childrenName) {
				node.put(key, entry.getValue());
			}
		}
		node.put(pid, parentId);
		result.add(node);
		
		//处理子节点数据
		Object obj = map.get(childrenName);
		if(obj instanceof List) {
			List<HashMap<String, Object>> childrenList = (List<HashMap<String, Object>>) obj;
			for(int i=0;i<childrenList.size();i++) {
				mapToFlat(childrenList.get(i),(String)map.get(id),id,pid,childrenName,result);
			}
		}else if(obj instanceof HashMap) {
			HashMap<String, Object> childMap = (HashMap<String, Object>) obj;
			mapToFlat(childMap,(String)map.get(id),id,pid,childrenName,result);
		}
		return result;
	}
	
	/**
	 * 扁平树形数据->转换->标准树形数据.	扁平树形数据-id节点属性名称，默认id,扁平树形数据-父id节点属性名称，默认pid,生成的标准树形数据-子节点的属性名称，默认children
	 * @param jsonArr	json数组，java格式规范：List<HashMap<String, Object>>
	 * @return		标准树形数据
	 * @Author		姜宝俊
	 * @Group 		技术组
	 * @Worker	 	1861 
	 * @date 		2018年6月4日 下午2:47:21     
	 * @Company		Vstsoft
	 * @version 	V1.0
	 */
	public static List<HashMap<String, Object>> flatToTree(JSONArray jsonArr){
		List<HashMap<String, Object>> tlist = transferData(jsonArr);
		return flatToTree(tlist,T_ID,T_PID,T_CHILDRENNAME);
	}
	
	/**
	 * 扁平树形数据->转换->标准树形数据
	 * @param jsonArr	扁平树形数据
	 * @param id	扁平树形数据-id节点属性名称，默认id
	 * @param pid	扁平树形数据-父id节点属性名称，默认pid
	 * @param childrenName	生成的标准树形数据-子节点的属性名称，默认children
	 * @return		标准树形数据
	 * @Author		姜宝俊
	 * @Group 		技术组
	 * @Worker	 	1861 
	 * @date 		2018年6月4日 下午2:56:26     
	 * @Company		Vstsoft
	 * @version 	V1.0
	 */
	public static List<HashMap<String, Object>> flatToTree(JSONArray jsonArr, String id, String pid, String childrenName){
		List<HashMap<String, Object>> tlist = transferData(jsonArr);
		return flatToTree(tlist,id,pid,childrenName);
	}
	
	/**
	 * 扁平树形数据->转换->标准树形数据.	扁平树形数据-id节点属性名称，默认id,扁平树形数据-父id节点属性名称，默认pid,生成的标准树形数据-子节点的属性名称，默认children
	 * @param list	扁平树形数据
	 * @return		标准树形数据
	 * @Author		姜宝俊
	 * @Group 		技术组
	 * @Worker	 	1861 
	 * @date 		2018年6月4日 上午10:00:21     
	 * @Company		Vstsoft
	 * @version 	V1.0
	 */
	public static List<HashMap<String, Object>> flatToTree(List<HashMap<String, Object>> list){
		return flatToTree(list,T_ID,T_PID,T_CHILDRENNAME);
	}
	
	/**
	 * 扁平树形数据->转换->标准树形数据
	 * @param list	扁平树形数据
	 * @param id	扁平树形数据-id节点属性名称，默认id
	 * @param pid	扁平树形数据-父id节点属性名称，默认pid
	 * @param childrenName	生成的标准树形数据-子节点的属性名称，默认children
	 * @return		标准树形数据
	 * @Author		姜宝俊
	 * @Group 		技术组
	 * @Worker	 	1861 
	 * @date 		2018年6月1日 下午4:51:27     
	 * @Company		Vstsoft
	 * @version 	V1.0
	 */
	public static List<HashMap<String, Object>> flatToTree(List<HashMap<String, Object>> list, String id, String pid, String childrenName){
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		if(id==null) id = T_ID;
		if(pid==null) pid = T_PID;
		if(childrenName==null) childrenName = T_CHILDRENNAME;
		if(list==null ||list.size()<1) {
			return result;
		}
		//创建临时映射(节点id：节点)
		HashMap<String, HashMap<String, Object>> tmpMap = new HashMap<String, HashMap<String, Object>>();
		for(int i=0;i<list.size();i++) {
			//节点id
			Object o = list.get(i).get(id);
			if(o==null) {
				return backErr("对不起，您传入的数据不符合规范，某些项缺失属性："+id);
			}
			String key = list.get(i).get(id).toString();
			tmpMap.put(key, list.get(i));
		}
		//设置children节点
		for(int i=0;i<list.size();i++) {
			HashMap<String, Object> currentNode = list.get(i);
			//当前父节点
			Object parentKeyObj = list.get(i).get(pid);
			HashMap<String, Object> parentNode = null;
			if(parentKeyObj!=null) {
				parentNode = tmpMap.get(parentKeyObj.toString());
			}
			//判断父节点是否存在,不存在证明是根节点
			if(parentNode != null) {
				//初始化children子节点属性对象值
				ArrayList<HashMap<String, Object>> childrenList = (ArrayList<HashMap<String, Object>>) parentNode.get(childrenName);
				if(childrenList==null) {
					childrenList = new ArrayList<HashMap<String, Object>>();
					parentNode.put(childrenName, childrenList);
				}
				//设置子节点
				childrenList.add(list.get(i));
			}else {
				result.add(list.get(i));
			}
		}
		return result;
	}
	
	/**
	 * 返回错误值
	 * @param info 错误描述
	 * @return		错误信息
	 * @Author		姜宝俊
	 * @Group 		技术组
	 * @Worker	 	1861 
	 * @date 		2018年6月4日 下午2:52:35     
	 * @Company		Vstsoft
	 * @version 	V1.0
	 */
	private static List<HashMap<String, Object>> backErr(String info) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("info",info);
		list.add(map);
		return list;
	}

	/**
	 * fastjson转换list
	 * @param jsonArr	fastjson数组
	 * @return		list
	 * @Author		姜宝俊
	 * @Group 		技术组
	 * @Worker	 	1861 
	 * @date 		2018年6月4日 下午2:51:33     
	 * @Company		Vstsoft
	 * @version 	V1.0
	 */
	private static List<HashMap<String, Object>> transferData(JSONArray jsonArr) {
		List<HashMap<String, Object>> tlist = new ArrayList<HashMap<String, Object>>();
		try {
			for(int i=0;i<jsonArr.size();i++) {
				Object map = jsonArr.get(i);
				String json = JSON.toJSONString(map,true);
		        HashMap<String,Object> map1 = JSON.parseObject(json, HashMap.class);
		        tlist.add(map1);
			}
		} catch (Exception e) {
			return backErr("您传入的数据不符合规范");
		}
		return tlist;
	}
	
	//扁平数据
	private static List<HashMap<String, Object>> getFlatData() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> m1 = new HashMap<String, Object>();
		HashMap<String, Object> m2 = new HashMap<String, Object>();
		HashMap<String, Object> m3 = new HashMap<String, Object>();
		HashMap<String, Object> m4 = new HashMap<String, Object>();
		HashMap<String, Object> m5 = new HashMap<String, Object>();
		HashMap<String, Object> m6 = new HashMap<String, Object>();
		HashMap<String, Object> m7 = new HashMap<String, Object>();
		HashMap<String, Object> m8 = new HashMap<String, Object>();
		m1.put("Id","1");m1.put("Name","中国");m1.put("pid","");
		m2.put("Id","2");m2.put("Name","英国");m2.put("pid","");
		m3.put("Id","4DDA93E00CD34E4D812EC1A9E10AA883");m3.put("Name","河北");m3.put("pid","1");
		m4.put("Id","6CD3A04CFBC1419F81E1A66BDC81F177");m4.put("Name","承德");m4.put("pid","4DDA93E00CD34E4D812EC1A9E10AA883");
		m5.put("Id","B93352644544420782337BC41C0534A9");m5.put("Name","石家庄");m5.put("pid","4DDA93E00CD34E4D812EC1A9E10AA883");
		m6.put("Id","SF789Y3489RSDFJ8394TR03RFW23");m6.put("Name","长安区");m6.put("pid","B93352644544420782337BC41C0534A9");
		m7.put("Id","68F89C4E368048E699F3D7EDD69A86A7");m7.put("Name","江苏");m7.put("pid","1");
		m8.put("Id","CF31D0CA5BC34765A61909B296E470C6");m8.put("Name","山东");m8.put("pid","1");
		list.add(m1);list.add(m2);list.add(m3);list.add(m4);list.add(m5);list.add(m6);list.add(m7);list.add(m8);
		return list;
	}
	//数组树形数据
	private static List<HashMap<String, Object>> getListTreeData() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> m1 = new HashMap<String, Object>();
		HashMap<String, Object> m2 = new HashMap<String, Object>();
		HashMap<String, Object> m11 = new HashMap<String, Object>();
		HashMap<String, Object> m12 = new HashMap<String, Object>();
		HashMap<String, Object> m13 = new HashMap<String, Object>();
		HashMap<String, Object> m111 = new HashMap<String, Object>();
		HashMap<String, Object> m112 = new HashMap<String, Object>();
		HashMap<String, Object> m1121 = new HashMap<String, Object>();
		m1.put("Id","1");m1.put("Name","中国");
		m2.put("Id","2");m2.put("Name","英国");
		m11.put("Id","4DDA93E00CD34E4D812EC1A9E10AA883");m11.put("Name","河北");
		m111.put("Id","6CD3A04CFBC1419F81E1A66BDC81F177");m111.put("Name","承德");
		m112.put("Id","B93352644544420782337BC41C0534A9");m112.put("Name","石家庄");
		m1121.put("Id","SF789Y3489RSDFJ8394TR03RFW23");m1121.put("Name","长安区");
		m12.put("Id","68F89C4E368048E699F3D7EDD69A86A7");m12.put("Name","江苏");
		m13.put("Id","CF31D0CA5BC34765A61909B296E470C6");m13.put("Name","山东");
		
		List<HashMap<String, Object>> provences = new ArrayList<HashMap<String, Object>>();
		provences.add(m11);provences.add(m12);provences.add(m13);
		m1.put("children",provences);
		
		List<HashMap<String, Object>> cities = new ArrayList<HashMap<String, Object>>();
		cities.add(m111);cities.add(m112);
		m11.put("children",cities);
		
		m112.put("children",m1121);
		
		list.add(m1);
		list.add(m2);
		
		return list;
	}
	//对象树形数据
	private static HashMap<String, Object> getObjectTreeData() {
		HashMap<String, Object> m1 = new HashMap<String, Object>();
		HashMap<String, Object> m11 = new HashMap<String, Object>();
		HashMap<String, Object> m12 = new HashMap<String, Object>();
		HashMap<String, Object> m13 = new HashMap<String, Object>();
		HashMap<String, Object> m111 = new HashMap<String, Object>();
		HashMap<String, Object> m112 = new HashMap<String, Object>();
		HashMap<String, Object> m1121 = new HashMap<String, Object>();
		m1.put("Id","1");m1.put("Name","中国");
		m11.put("Id","4DDA93E00CD34E4D812EC1A9E10AA883");m11.put("Name","河北");
		m111.put("Id","6CD3A04CFBC1419F81E1A66BDC81F177");m111.put("Name","承德");
		m112.put("Id","B93352644544420782337BC41C0534A9");m112.put("Name","石家庄");
		m1121.put("Id","SF789Y3489RSDFJ8394TR03RFW23");m1121.put("Name","长安区");
		m12.put("Id","68F89C4E368048E699F3D7EDD69A86A7");m12.put("Name","江苏");
		m13.put("Id","CF31D0CA5BC34765A61909B296E470C6");m13.put("Name","山东");
		
		List<HashMap<String, Object>> provences = new ArrayList<HashMap<String, Object>>();
		provences.add(m11);provences.add(m12);provences.add(m13);
		m1.put("children",provences);
		
		List<HashMap<String, Object>> cities = new ArrayList<HashMap<String, Object>>();
		cities.add(m111);cities.add(m112);
		m11.put("children",cities);
		
		m112.put("children",m1121);
		
		return m1;
	}
	
	
}
