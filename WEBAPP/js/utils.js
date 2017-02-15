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
    function getQueryString(name) {
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


    function alert(msg){
        UIkit.modal.alert(msg);
    }

    //展示错误信息
    function alertError(e){
        var errMsg = "<h3 class='uk-text-danger uk-text-bold'>Ops , We hava an error!</h3><div style='margin: 0px 15px 0px 15px;'>";
        if(e instanceof Error) {
            if(e.name == "Error") {
                var errObj = eval("err_" + currentTimeMills() + " = " + e.message);

                if(errObj.errClass == "java.nio.channels.InterruptedByTimeoutException"){
                    errMsg = errMsg + "Network time out, try connect again."
                } else if (errObj.errMsg != null) {
                    errMsg = errMsg + errObj.errMsg.replaceAll("\\\"", "\"");
                } else {
                    errMsg = errMsg + errObj.errClass;
                }
            } else if(e.name == "NetworkError"){
                errMsg = "Network has some problem, check it."
            }
        }else {
            errMsg = errMsg + "[" + e.name+ "] "+ e.message;
        }
        errMsg = errMsg+"</div>"
        alert(errMsg);
    }

    //创建终端
    function terminal(elementFlag) {
        var term = new Terminal({
            cursorBlink: true,
            cols: 120,
            rows: 20
        });
        term.capsLock = false
        term.open(document.getElementById(elementFlag));
        return term;
        //term.write('Hello from \033[1;3;31mxterm.js\033[0m $ ')
    }

/**
 * =======================vuejs 过滤器函数=======================
 */
    //Vue 的自定义过滤器
    function delFirestChar(value){
        if(value==undefined){
            return null;
        }
        return value.substr(1,value.length)
    }

    function shortDockerId(value,length){
        if(value==undefined){
            return null;
        }
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

    String.prototype.replaceAll = function (sptr, sptr1){
        var str = this;
        while (str.indexOf(sptr) >= 0){
            str = str.replace(sptr, sptr1);
        }
        return str;
    }

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
            len=this.length,
            isNumber=false;

        if(typeof prop=='string') {
            for(; i<len; i++){
                var oI = this[i];
                if(typeof oI[prop] == "number"){
                    (props[i] = new Number(oI && oI[prop] || ''))._obj = oI;
                    isNumber = true;
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
                    isNumber=true;
                }else {
                    (props[i] = new String(oI && prop(oI) || ''))._obj = oI;
                }
            }
        }
        else {
            throw '参数类型错误';
        }

        if(isNumber){
            props.sort(function(a,b){
                if(a>b){
                    return true;
                }else{
                    return false;
                }
            });
        }else {
            props.sort();
        }
        for(i=0; i<len; i++) {
            ret[i] = props[i]._obj;
        }
        if(desc) ret.reverse();
        return ret;
    };

    Array.prototype.remove=function(index) {
        if(isNaN(index)||index>this.length){
            return false;
        }
        this.splice(index,1);

    }