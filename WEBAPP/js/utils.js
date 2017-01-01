
//菜单选中标识
function mainMenuActive(event){
    $(event.target).parent().parent().children("li[class*='uk-active']").each(function(index,eli){
        $(eli).removeClass("uk-active")
    })
    $(event.target).parent().addClass("uk-active")
}


//导入后台类
function doImport(classPath){
    var scriptURL = "../../../../../ajax/do/genScript/"+classPath
    $("head").after('<script lang="javascript" src="'+scriptURL+'"/>')

}

//展示错误信息
function alertError(e){
    var currentTime = new Date().getTime();
    errObj = eval("err_"+currentTime+" = "+e.message);
    if(errObj.errMsg!=""){
        UIkit.modal.alert(errObj.errMsg);
    }
}


//Vue 的自定义过滤器
function delFirestChar(value){
    return value.substr(1,value.length)
}

function shortDockerId(value,length){
    if(value.startWith("sha256:")){
        value = value.replace("sha256:","")
        value = value.substr(0,length);
    }

    if(value.indexOf(".") && value.length > 16){
        value = value.substr(0,length) + "...";
    }

    return value;
}

Vue.filter('delFirestChar',delFirestChar);

Vue.filter('shortDockerId',shortDockerId);

//扩展字符串的方法
String.prototype.endWith=function(s){
    if(s==null||s==""||this.length==0||s.length>this.length)
        return false;
    if(this.substring(this.length-s.length)==s)
        return true;
    else
        return false;
    return true;
}

String.prototype.startWith=function(s){
    if(s==null||s==""||this.length==0||s.length>this.length)
        return false;
    if(this.substr(0,s.length)==s)
        return true;
    else
        return false;
    return true;
}