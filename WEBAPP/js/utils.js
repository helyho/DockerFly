/**
 * =======================工具函数=======================
 */
    //菜单选中标识
    function mainMenuActive(event){
        $(event.target).parent().parent().children("li[class*='uk-active']").each(function(index,eli){
            $(eli).removeClass("uk-active")
        })
        $(event.target).parent().addClass("uk-active")
    }

    //取 URL 中的参数
    function GetQueryString(name) {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)return  unescape(r[2]); return null;
    }

    //导入后台类
    function doImport(classPath){
        var scriptURL = "../../../../../ajax/do/genScript/"+classPath
        $("head").after('<script lang="javascript" src="'+scriptURL+'"/>')

    }

    //取系统毫秒累计时间
    function currentTimeMills(){
        return new Date().getTime();
    }

    //展示错误信息
    function alertError(e){
        try {
            var errObj = eval("err_" + currentTimeMills() + " = " + e.message);
            UIkit.modal.alert(errObj.errMsg);
        }catch(e){
            UIkit.modal.alert(e.message);
        }
    }


/**
 * =======================vuejs 过滤器函数=======================
 */
    //Vue 的自定义过滤器
    function delFirestChar(value){
        return value.substr(1,value.length)
    }

    function shortDockerId(value,length){
        if(value.startsWith("sha256:")){
            value = value.replace("sha256:","")
            value = value.substr(0, length);
        }

        if(value.indexOf(".") && value.length > 16){
            value = value.substr(0,length);
        }

        return value;
    }

    function strToDate(value){
        value = value.replace("T"," ");
        value = value.substr(0,value.indexOf("."));
        return value
    }

    Vue.filter('delFirestChar',delFirestChar);

    Vue.filter('shortDockerId',shortDockerId);
    Vue.filter('strToDate',strToDate);

/**
 * =======================基础类扩展函数=======================
 */
    // 对Date的扩展，将 Date 转化为指定格式的String
    // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
    // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
    // 例子：
    // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
    // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
    Date.prototype.format = function(fmt) {
        var o = {

            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }


    Array.prototype.sortBy=function (prop, desc){
        var props=[],
            ret=[],
            i=0,
            len=this.length;
        if(typeof prop=='string') {
            for(; i<len; i++){
                var oI = this[i];
                if(typeof oI[prop] == "number"){
                    (props[i] = new Number(oI && oI[prop] || ''))._obj = oI;
                }else {
                    (props[i] = new String(oI && oI[prop] || ''))._obj = oI;
                }
            }
        }
        else if(typeof prop=='function') {
            for(; i<len; i++){
                var oI = this[i];
                if(typeof prop(oI) == "number"){
                    (props[i] = new Number(oI && prop(oI) || ''))._obj = oI;
                }else {
                    (props[i] = new String(oI && prop(oI) || ''))._obj = oI;
                }
            }
        }
        else {
            throw '参数类型错误';
        }
        props.sort();
        for(i=0; i<len; i++) {
            ret[i] = props[i]._obj;
        }
        if(desc) ret.reverse();
        return ret;
    };