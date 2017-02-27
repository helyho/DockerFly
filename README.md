![DockerFly](http://git.oschina.net/uploads/images/2017/0227/130240_f5a011b5_116083.png "DockerFly")
------------------

####Dockerfly是基于 Docker1.12+ (Docker API 1.24+) 开发出Docker 管理工具,提供里最基本的基于 Docker 的管理功能,目的是能够方便广大Docker初学者以及 Docker 管理员能够快速的进行Docker 容器的管理和维护。

------------------

###使用方法
 - Clone 后在本地使用
```shell
mkdir DockerFly
cd DockerFly
git clone https://git.oschina.net/helyho/DockerFly.git
./start.sh
```
    访问:http://127.0.0.1:28083即刻

 - 直接 pull 一个 dockerfly 容器
```shell
    docker pull registry.cn-hangzhou.aliyuncs.com/voovan/dockerfly:<version>
    docker run --name dockerfly -d -v /var/run/docker.sock:/var/run/docker.sock -p 2735:2735 -p 28083:28083 dockerfly
```
    访问:http://127.0.0.1:28083即刻

------------------
####功能概览
![功能概览](http://git.oschina.net/uploads/images/2017/0227/125457_aa86c71d_116083.gif "功能概览")
------------------
####容器操作
![容器操作](http://git.oschina.net/uploads/images/2017/0227/125522_866b27ec_116083.gif "容器操作")

------------------
版本说明:
 - `20170222` 首个发布版本

------------------
**使用到了 Voovan 项目**

 - [https://git.oschina.net/helyho/Voovan](https://git.oschina.net/helyho/Voovan)

**以及两个子项目:**
 - [https://git.oschina.net/helyho/JDocker](https://git.oschina.net/helyho/JDocker)
 - [https://git.oschina.net/helyho/Vestful](https://git.oschina.net/helyho/Vestful)
