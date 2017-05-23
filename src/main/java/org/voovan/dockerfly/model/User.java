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
    private Integer defaultHost;
    private List<Host> hosts;
    private List<Registry> registrys;
    private Timestamp createDate;

    public User(){
        hosts = new ArrayList<Host>();
        registrys = new ArrayList<Registry>();
        role = "User";
    }

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
        hosts = new ArrayList<Host>();
        registrys = new ArrayList<Registry>();
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

    public Integer getDefaultHost() {
        return defaultHost;
    }

    public void setDefaultHost(Integer defaultHost) {
        this.defaultHost = defaultHost;
    }

    public List<Registry> getRegistrys() {
        return registrys;
    }

    public void setRegistrys(List<Registry> registrys) {
        this.registrys = registrys;
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
