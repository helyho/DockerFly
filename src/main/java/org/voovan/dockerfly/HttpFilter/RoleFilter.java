package org.voovan.dockerfly.HttpFilter;

import org.voovan.docker.command.Cmd;
import org.voovan.dockerfly.db.DataBaseUtils;
import org.voovan.dockerfly.model.User;
import org.voovan.http.server.HttpFilter;
import org.voovan.http.server.HttpRequest;
import org.voovan.http.server.HttpResponse;
import org.voovan.http.server.HttpSession;
import org.voovan.http.server.context.HttpFilterConfig;
import org.voovan.tools.ObjectPool;
import org.voovan.tools.TString;
import org.voovan.tools.json.JSON;
import org.voovan.tools.log.Logger;
import org.voovan.tools.reflect.TReflect;
import org.voovan.vestful.VestfulGlobal;
import org.voovan.vestful.dto.Error;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 类文字命名
 *
 * @author: helyho
 * DockerFly Framework.
 * WebSite: https://github.com/helyho/DockerFly
 * Licence: Apache v2 License
 */
public class RoleFilter implements HttpFilter {

    /**
     * 尝试初始化数据库
     * @param request 请求对象
     * @param response 响应对象
     */
    public void tryInitDataBase(HttpRequest request, HttpResponse response){
        String requestPath = request.protocol().getPath();
        // 登录页面尝试初始化数据库
        if(requestPath.endsWith("login.html")){
            DataBaseUtils.initDataBase();
        }
    }

    /**
     * 检查静态页面是否登录
     * @param request 请求对象
     * @param response 响应对象
     */
    public void checkPageIsLogin(HttpRequest request, HttpResponse response) {
        String requestPath = request.protocol().getPath();
        User user = getUserFromSession(request);

        // 除登录页面,在没有登录时,全部转向到登录页面
        if ((requestPath.endsWith(".html") || requestPath.endsWith("/"))
                && !requestPath.endsWith("login.html") && user == null) {
            response.redirct("/login.html");
            DataBaseUtils.initDataBase();
        }

        if(requestPath.endsWith("logout.html")){
            HttpSession session = request.getSession();
            session.removeAttribute("User");
            response.redirct("/login.html");
        }
    }

    /**
     * 创建对象权限管理
     * @param request 请求对象
     * @param response 响应对象
     */
    public void onCreateObject(HttpRequest request, HttpResponse response){
        String requestPath = request.protocol().getPath();
        //DirectObject 创建对象方法捕获类名
        if(requestPath.endsWith("createObject")){
            String className = request.getParameter("className");
        }
    }

    /**
     * 用户管理操作(OperUser类的方法)控制
     * @param request 请求对象
     * @param response 响应对象
     */
    public void userOperation(HttpRequest request, HttpResponse response){
        ObjectPool objectPool = VestfulGlobal.getObjectPool();
        String objectId = request.getParameter("pooledObjectId");
        Object obj = objectPool.get(objectId);
        String className = obj.getClass().getName();
        String methodName = request.getParameter("methodName");
        List params = request.getParameterAsObject("params",List.class);
        User user = getUserFromSession(request);

        //检查修改用户属性的操作是否合法
        if(className.equals("org.voovan.dockerfly.DataOperate.OperUser")){
            switch (methodName){
                case "checkUser" :
                    return;
                //判断单用户操作是否合法
                case "modifyPassword" :;
                case "modifyHosts":;
                case "modifyDefaultHost":;
                case "getUser": {
                    if(user != null) {
                        //用户 ID 和参数中提供的 ID 不同的时候,且不是 Admin 类型的用户,返回错误
                        if (params != null && user.getUserId() != (Integer) params.get(0)) {
                            if (!user.getRole().equals("Admin")) {
                                error("NO_RIGHT", request, response);
                            }
                        }
                    } else {
                        disposeNotLogin(request, response);
                    }
                };

                //只有管理员用户可以执行的操作
                case "getUserList":;
                case "addUser":;
                case "delUser":{
                    if(user!=null) {
                        if (!user.getRole().equals("Admin")) {
                            error("NO_RIGHT", request, response);
                        }
                    } else {
                        disposeNotLogin(request, response);
                    }
                };
            }
        }
    }

    /**
     * JDocker方法操作控制
     * @param request 请求对象
     * @param response 响应对象
     */
    public void dockerOperation(HttpRequest request, HttpResponse response){
        ObjectPool objectPool = VestfulGlobal.getObjectPool();
        String objectId = request.getParameter("pooledObjectId");
        Object obj = objectPool.get(objectId);
        String className = obj.getClass().getName();
        String methodName = request.getParameter("methodName");
        User user = getUserFromSession(request);

        if(TString.regexMatch(className,"org\\.voovan\\.docker\\.command") > 0) {
            if (user != null) {
                //创建和查看 Docker 实体在 Label 中增加用户标志 (针对User用户类型进行处理)
                if (className.matches("org\\.voovan\\.docker\\.command\\..*?\\.Cmd[^(Image)].*?(Create|List)") &&
                        methodName.equals("send") &&
                        user.getRole().equals("User")) {
                    try {
                        Method method = TReflect.findMethod(obj.getClass(), "label", new Class[]{String.class, String.class});
                        if (method != null) {
                            TReflect.invokeMethod(obj, method, "org.voovan.dockerfly.label.UserId", String.valueOf(user.getUserId()));
                            TReflect.invokeMethod(obj, method, "org.voovan.dockerfly.label.UserName", user.getUserName());
                        }
                    } catch (ReflectiveOperationException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                disposeNotLogin(request, response);
            }
        }
    }

    /**
     * 处理为登录操作
     * @param request 请求对象
     * @param response 响应对象
     */
    public void disposeNotLogin(HttpRequest request, HttpResponse response){
        ObjectPool objectPool = VestfulGlobal.getObjectPool();
        String objectId = request.getParameter("pooledObjectId");
        Object obj = objectPool.get(objectId);
        if(obj instanceof Cmd){
            ((Cmd)obj).close();
        }
        objectPool.remove(objectId);
        error("NOT_LOGIN", request, response);
    }

    /**
     * 方法调用权限控制
     * @param request 请求对象
     * @param response 响应对象
     */
    public void onInvoke(HttpRequest request, HttpResponse response){
        String requestPath = request.protocol().getPath();

        //DirectObject 调用对象方法权限判断
        if(requestPath.endsWith("invoke")){
            userOperation(request, response);
            dockerOperation(request, response);
        }

    }

    @Override
    public Object onRequest(HttpFilterConfig filterConfig, HttpRequest request,
                            HttpResponse response, Object prevFilterResult)  {
        HttpSession session = request.getSession();
        User user = getUserFromSession(request);
        String requestPath = request.protocol().getPath();

        // 登录页面尝试初始化数据库
        tryInitDataBase(request, response);

        //检查是否登录
        //除登录页面,在没有登录时,全部转向到登录页面
        checkPageIsLogin(request, response);

        //DirectObject 创建对象方法捕获类名
        onCreateObject(request, response);

        onInvoke(request, response);

        return null;
    }

    @Override
    public Object onResponse(HttpFilterConfig filterConfig, HttpRequest request, HttpResponse response, Object prevFilterResult) {

        User user = getUserFromSession(request);
        String requestPath = request.protocol().getPath();

        HttpSession session = request.getSession();
        //DirectObject 调用对象方法
        if(requestPath.endsWith("invoke")){

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

    /**
     * 从会话对象获取用户对象
     * @param request 请求对象
     * @return 用户对象
     */
    public static User getUserFromSession(HttpRequest request){
        HttpSession session = request.getSession();
        User user = null;
        if(session.containAttribute("User")) {
            user = (User)session.getAttribute("User");
        }
        return user;
    }

    /**
     * 构造错误信息
     * @param type 错误类型,此类型需要和前端 js 匹配
     * @param request 请求对象
     * @param response 响应对象
     */
    public static void error(String type, HttpRequest request,HttpResponse response){
        String result = Error.newInstance(request.protocol().getPath(), type, "You didn't hava right.").toString();
        response.protocol().setStatus(506);
        response.body().write(result);
    }
}
