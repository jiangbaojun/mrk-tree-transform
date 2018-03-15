/* -----------------------------------------------
/* Author : jiangbaojun
/* Demo
/* GitHub : https://github.com/jiangbaojun/mrk-tree-transform.git
/* How to use? : Check the GitHub README
/* v1.0
/* ----------------------------------------------- */


(function (window){
	var handle = {
		_treeToFlat: function(data, options) {
			var self = this;
			var idName = options.idName || "id", 
				pidName = options.pidName || "pid", 
				childrenName = options.childrenName || "children", 
				parentId = options.parentId || "", 
				array = options.array || [];
			var node = {};
			for(key in data){
				if(key!=childrenName){
					node[key] = data[key];
				}
			}
			node[pidName] = parentId;
			array.push(node);
			//子节点数组
			if(Object.prototype.toString.call(data[childrenName])==='[object Array]' && (data[childrenName]).length>0){
				for(var i=0;i<(data[childrenName]).length;i++){
					self._treeToFlat(data[childrenName][i], {idName:idName, pidName:pidName, childrenName:childrenName, parentId:data[idName], array:array});
				}
			}
			//子节点对象
			if(Object.prototype.toString.call(data[childrenName])==='[object Object]'){
				self._treeToFlat(data[childrenName], {idName:idName, pidName:pidName, childrenName:childrenName, parentId:data[idName], array:array});
			}
			return array;
		}	
	}
	/**
	 * @Comment			将标准树转换为扁平树
	 * @param data		标准树形结构数据
	 * @param options	必要选项
	 *			idName			节点id属性名称,默认"id"
	 *			pidName			关联父级节点id属性名称,默认"pid"
	 *			childrenName 	子节点属性名称,默认"children"
	 *			parentId		顶级父节点属性值，默认""
	 * @returns 	扁平树结构数据
	 * @author		姜宝俊
	 * @Version		1.0
	 */
	function treeToFlat(data, options) {
		//数组
		var resultArr = [];
		if(Object.prototype.toString.call(data)==='[object Array]' && data.length>0){
			for(var i=0;i<data.length;i++){
				resultArr = resultArr.concat(handle._treeToFlat(data[i], options));
			}
		}
		//对象
		else if(Object.prototype.toString.call(data)==='[object Object]'){
			resultArr = handle._treeToFlat(data, options);
		}
		return resultArr;
	}


	/**
	 * @Comment			将扁平树转化为标准树
	 * @param list		扁平树数据（一个数组）
	 * @param options	必要选项
	 *			idName			节点id属性名称,默认"id"
	 *			pidName			关联父级节点id属性名称,默认"pid"
	 *			childrenName 	子节点属性名称,默认"children"
	 * @returns 
	 * @author		姜宝俊
	 * @Version		1.0
	 */
	function flatToTree(list, options){
		var idName = options.idName || "id", 
			pidName = options.pidName || "pid", 
			childrenName = options.childrenName || "children";
		var result = [],temp = {};  
		for(i = 0; i < list.length; i++){
			//将扁平树数组转成对象类型
			temp[list[i][idName]]=list[i];
		}  
		for(j=0; j<list.length; j++){
			//获取每一个子对象的父对象  
			tempVp = temp[list[j][pidName]];
			//判断父对象是否存在，如果不存在直接将对象放到第一层  
			if(tempVp){
				//如果父元素节点不存在，则创建数组  
				if(!tempVp[childrenName]){
					tempVp[childrenName] = [];	
				}
				//将本对象压入父对象的数组  
				tempVp[childrenName].push(list[j]);
			}else{
				//将不存在父对象的对象直接放入一级目录  
				result.push(list[j]);
			}  
		}  
		return result;  
	}
	window.treeToFlat=treeToFlat;
	window.flatToTree=flatToTree;
}(window));