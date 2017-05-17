package org.voovan.dockerfly.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户模型
 *
 * @author: helyho
 * DockerFly Framework.
 * WebSite: https://github.com/helyho/DockerFly
 * Licence: Apache v2 License
 */
public class User {
    private int userId;
    private String userName;
    private String password;
    private String role;
    private Integer usingHost;
    private List<Host> hosts;
    private Timestamp createDate;

    public User(){
        hosts = new ArrayList<Host>();
        role = "User";
    }

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
        hosts = new ArrayList<Host>();
        role = "User";
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getUsingHost() {
        return usingHost;
    }

    public void setUsingHost(Integer usingHost) {
        this.usingHost = usingHost;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}
