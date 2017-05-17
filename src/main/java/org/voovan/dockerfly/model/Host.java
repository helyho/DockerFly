package org.voovan.dockerfly.model;

import org.voovan.docker.DockerGlobal;

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
    private int timeout;
    private boolean debug;
    private String name;

    public Host(String name, String ipAddress, int port){
        this.name = name;
        this.ipAddress = ipAddress;
        this.port = port;
        this.timeout= DockerGlobal.DOCKER_REST_TIMEOUT;
        this.debug= DockerGlobal.DEBUG;
    }

    public Host(String name, String ipAddress, int port, int timeout, boolean debug){
        this.name = name;
        this.ipAddress = ipAddress;
        this.port = port;
        this.timeout= timeout;
        this.debug= debug;
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

    public static Host newInstanec(String name, String ipAddress, int port, int timeout, boolean debug){
        return new Host(name, ipAddress, port, timeout, debug);
    }
}
