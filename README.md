![DockerFly](http://git.oschina.net/uploads/images/2017/0227/130240_f5a011b5_116083.png "DockerFly")
------------------

#####Dockerfly是基于 Docker1.12+ (Docker API 1.24+) 开发出Docker 管理工具,提供里最基本的基于 Docker 的管理功能,目的是能够方便广大Docker初学者以及 Docker 管理员能够快速的进行Docker 容器的管理和维护。

------------------
###最新版本:
 - `20170227` 首个发布版本

 请在 pull 的时候,替换版本号<version>。



------------------
###使用方法
 - Clone 后在本地使用
```shell
mkdir DockerFly
cd DockerFly
git clone https://git.oschina.net/helyho/DockerFly.git
./start.sh
```
    > `start.sh` 脚本依赖 socat,所以请您先安装 socat 到您的系统中,或者您已经暴露了一个 docker 的 TCP 端口,请删除对应的命令。
    访问:http://127.0.0.1:28083

 - 直接 pull 一个 dockerfly 容器
```shell
    docker pull registry.cn-hangzhou.aliyuncs.com/voovan/dockerfly:<version> 
    docker run --name dockerfly -d -v /var/run/docker.sock:/var/run/docker.sock -p 2735:2735 -p 28083:28083 dockerfly
```
    访问:http://127.0.0.1:28083

------------------

####功能概览
![功能概览](http://git.oschina.net/uploads/images/2017/0227/125457_aa86c71d_116083.gif "功能概览")
------------------
####容器操作
![容器操作](http://git.oschina.net/uploads/images/2017/0227/125522_866b27ec_116083.gif "容器操作")

------------------

###相关项目
Dockerfly 是一个开源项目Voovan 的子项目,如果您觉得软件好用请推广给你的朋友并请您抽出宝贵时间 Star 一下项目。感谢各位的支持！！！

**Voovan项目**
 - [https://git.oschina.net/helyho/Voovan](https://git.oschina.net/helyho/Voovan) 

**JDocker:**
 - [https://git.oschina.net/helyho/JDocker](https://git.oschina.net/helyho/JDocker)

**Vestful:**
 - [https://git.oschina.net/helyho/Vestful](https://git.oschina.net/helyho/Vestful)