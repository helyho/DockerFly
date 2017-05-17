package org.voovan.dockerfly.model;

/**
 * 类文字命名
 *
 * @author: helyho
 * DockerFly Framework.
 * WebSite: https://github.com/helyho/DockerFly
 * Licence: Apache v2 License
 */
public class Host {
    private String ipAddress;
    private int port;
    private String name;

    public Host(String name,String ipAddress, int port){
        this.name = name;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static Host newInstanec(String name, String ipAddress, int port){
        return new Host(name, ipAddress, port);
    }
}
