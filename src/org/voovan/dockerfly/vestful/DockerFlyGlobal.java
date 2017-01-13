package org.voovan.dockerfly.vestful;

import org.voovan.docker.DockerGlobal;

/**
 * 类文字命名
 *
 * @author helyho
 *         <p>
 *         DockerFly Framework.
 *         WebSite: https://github.com/helyho/DockerFly
 *         Licence: Apache v2 License
 */
public class DockerFlyGlobal {
    private static boolean init = initJDocker();

    public static boolean initJDocker(){
        DockerGlobal.DOCKER_REST_HOST = "home.helyho.com";
        DockerGlobal.DOCKER_REST_PORT = 2735;
        DockerGlobal.DEBUG = true;
        return true;
    }
}
