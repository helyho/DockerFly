package org.voovan.dockerfly.HttpFilter;

import org.voovan.dockerfly.model.User;
import org.voovan.http.server.HttpFilter;
import org.voovan.http.server.HttpRequest;
import org.voovan.http.server.HttpResponse;
import org.voovan.http.server.HttpSession;
import org.voovan.http.server.context.HttpFilterConfig;
import org.voovan.tools.json.JSON;
import org.voovan.tools.log.Logger;
import org.voovan.vestful.VestfulGlobal;
import org.voovan.vestful.dto.Error;

/**
 * 类文字命名
 *
 * @author: helyho
 * DockerFly Framework.
 * WebSite: https://github.com/helyho/DockerFly
 * Licence: Apache v2 License
 */
public class RoleFilter implements HttpFilter {
    @Override
    public Object onRequest(HttpFilterConfig filterConfig, HttpRequest request, HttpResponse response, Object prevFilterResult) {
        HttpSession session = request.getSession();
        User user = getUserFromSession(request);

        //DirectObject 创建对象
        if(request.protocol().getPath().endsWith("createObject")){
            String className = request.getParameter("className");

        }

        //DirectObject 调用对象方法
        if(request.protocol().getPath().endsWith("invoke")){

            String methodName = request.getParameter("methodName");
            String objectId = request.getParameter("pooledObjectId");
            Object obj = VestfulGlobal.getObjectPool().get(objectId);
            String className = obj.getClass().getName();

            //登录权限控制
            if( !(className.equals("org.voovan.dockerfly.DataOperate.OperUser") && methodName.equals("checkUser")) ){
                if(user == null){
                    noRightError(request, response);
                }
            }
        }
        return null;
    }

    @Override
    public Object onResponse(HttpFilterConfig filterConfig, HttpRequest request, HttpResponse response, Object prevFilterResult) {

        User user = getUserFromSession(request);

        HttpSession session = request.getSession();
        //DirectObject 调用对象方法
        if(request.protocol().getPath().endsWith("invoke")){

            String methodName = request.getParameter("methodName");

            if(methodName.equals("checkUser")){
                String body = response.body().getBodyString();
                if(!body.equals("null")){
                    user = JSON.toObject(body, User.class);
                    session.setAttribute("User", user);
                    Logger.simple("User ["+user.getUserName()+"] logined.");
                }
            }
        }

        return null;
    }

    public static User getUserFromSession(HttpRequest request){
        HttpSession session = request.getSession();
        User user = null;
        if(session.containAttribute("User")) {
            user = (User)session.getAttribute("User");
        }
        return user;
    }

    public static void noRightError(HttpRequest request,HttpResponse response){
        String result = Error.newInstance(request.protocol().getPath(), "NOT_LOGIN", "You didn't hava right.").toString();
        response.protocol().setStatus(506);
        response.body().write(result);
    }
}
