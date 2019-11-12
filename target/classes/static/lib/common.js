/*!
 * 2016-5-25 11:18:20
 * by xiao
 */
(function($) {
	/**
	 * 获取地址栏参数
	 * @param b
	 * @returns {null}
	 */
	$.getUrlParam = function(b) {
		var c = new RegExp("(^|&)" + b + "=([^&]*)(&|$)");
		var d = window.location.search.substr(1).match(c);
		if (d != null) {
			return unescape(d[2])
		}
		return null
	};
	/*
	 * 复杂ajax请求
	 * contentType:text/html;charset=utf-8 或 application/json;charset=utf-8 或 application/x-www-form-urlencoded;charset=utf-8
	 * callback:处理返回数据的函数，可以是一个函数名，可以是调用函数中的一个匿名函数
	 * author:xiao  2017年2月18日14:17:35
	 */
	$.ajaxFun = function(reqMethod, reqUrl, reqParam, contentType, dataType, callback) {
		var request = jQuery.ajax({type : reqMethod, url : reqUrl, data : reqParam, contentType : contentType, dataType : dataType});
		request.done(function(data) {
			try {
				callback(data);
			} catch (e) {
				//异常处理
			}
		});
		request.fail(function(textStatus, errorThrown) {
			//请求失败处理
		});
	};

	/*
	 * 表单ajax post请求，返回数据为json
	 * contentType:text/html;charset=utf-8,application/json;charset=utf-8,
	 * callback:处理返回数据的函数，可以是一个函数名，可以是调用函数中的一个匿名函数
	 * author:xiao  2017年2月18日14:18:55
	 */
	$.ajaxFormPost = function(reqUrl, reqParam,callback) {
		var request = jQuery.ajax({type : 'post', url : reqUrl, data : reqParam, contentType : 'application/x-www-form-urlencoded;charset=utf-8', dataType : 'json'});
		request.done(function(data) {
			try {
				callback(data);
			} catch (e) {
				//异常处理
			}
		});
		request.fail(function(textStatus, errorThrown) {
			//请求失败处理
		});
	};

	/*
	 * 表单ajax get请求，返回数据为json
	 * callback:处理返回数据的函数，可以是一个函数名，可以是调用函数中的一个匿名函数
	 * author:xiao  2017年9月18日09:53:49
	 */
	$.ajaxFormGet = function(reqUrl, reqParam,callback) {
		var request = jQuery.ajax({type : 'get', url : reqUrl, data : reqParam, contentType : 'application/x-www-form-urlencoded;charset=utf-8', dataType : 'json'});
		request.done(function(data) {
			try {
				callback(data);
			} catch (e) {
				//异常处理
			}
		});
		request.fail(function(textStatus, errorThrown) {
			//请求失败处理
		});
	};

	/**
	 * json post请求
	 * callback:处理返回数据的函数，可以是一个函数名，可以是调用函数中的一个匿名函数
	 * author:Xiao 2017年9月18日09:54:27
	 */
	$.ajaxJsonPost = function(reqUrl, reqParam,callback) {
		var request = jQuery.ajax({type : 'post', url : reqUrl, data : reqParam, contentType : 'application/json;charset=utf-8', dataType : 'json'});
		request.done(function(data) {
			try {
				callback(data);
			} catch (e) {
				//异常处理
			}
		});
		request.fail(function(textStatus, errorThrown) {
			//请求失败处理
		});
	};
	/**
	 * 清空表单,不兼容IE8,IE9未测
	 * @param form 表单DOM对象
	 */
	$.resetForm = function(form){
		var ipts = $(form).find("input[type='text']");
		var iptnms = $(form).find("input[type='number']");
		var chks = $(form).find("input[type='checkbox']");
		var sets = $(form).find("select");
		$.each(ipts, function(ix,value) {
			$(value).val("");
		});
		$.each(iptnms, function(ix,value) {
			$(value).val(0);
		});
		$.each(sets, function(ix,value) {
			$(value).find("option[value='0']").prop("selected","selected")
		});
		$.each(chks, function(ix,value) {
			$(value).removeProp("checked");
		});
	}
})(jQuery);

var yuJS = {
	// 编辑器参数
	kingEditorParams : {
		items:['undo', 'redo', '|', 'preview', 'code', 'cut', 'copy', 'paste',
			'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
			'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
			'superscript', 'selectall', '|', 'fullscreen',
			'formatblock',  'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
			'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|','table']
	},
	//创建富文本编辑器
	createEditor : function(select) {
		return KindEditor.create(select,jointJS.kingEditorParams);
	}
};

function startTime(){
	var now = new Date();
	var nyear=now.getFullYear();
	var nmon=checkTimemon(now.getMonth());
	var ndate=checkTime(now.getDate());
	var week=checkTime(now.getDay());
	var h = checkTime(now.getHours());
	var m=checkTime(now.getMinutes());
	var s=checkTime(now.getSeconds());
	if(week==1) oweek="星期一";
	if(week==2) oweek="星期二";
	if(week==3) oweek="星期三";
	if(week==4) oweek="星期四";
	if(week==5) oweek="星期五";
	if(week==6) oweek="星期六";
	if(week==0) oweek="星期日";
	$("#nowTime").html(""+nyear+"-"+nmon+"-"+ndate+" "+oweek+"&nbsp;&nbsp;"+h+":"+m+":"+s);
	t=setTimeout('startTime()',1000);
}
//只有计算月份才需要+1
function checkTimemon(i){
	if (i<9){
		i+=1;
		return "0" + i;
	}
	return i+1;
}

function checkTime(i) {
	if (i < 10) {
		i = "0" + i;
	}
	return i;
}
/**
 * 字符串格式化
 * @returns {string}
 */
String.prototype.format = function() {
	var result = this.split("").join(""),
		reg = /\{\d*\}/g,
		res = reg.exec(result);
	if (res) {
		for (var i = 0; i < this.length; i++) {
			result = result.replace(eval('/\\{' + i + '\\}/g'), arguments[i]);
		}
	}
	//replace()中的正则加变量必须转换，否则使用new RegExp()创建； /\{' + i + '\}/g只能解析为｛0｝，而不是字符串'{0}';
	return result;
};