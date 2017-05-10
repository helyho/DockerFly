![DockerFly](http://git.oschina.net/uploads/images/2017/0227/130240_f5a011b5_116083.png "DockerFly")
------------------

#####Dockerfly是基于 Docker1.12+ (Docker API 1.24+) 开发出Docker 管理工具,提供里最基本的基于 Docker 的管理功能,目的是能够方便广大Docker初学者以及 Docker 管理员能够快速的进行Docker 容器的管理和维护。
------------------
> 使用 dockerfly 可以管理docker中 swarm、container、network、volume、image 等等你在 docker 中想管理的所有东西。

> 通过 dockerfly 的swarm管理你可以轻松的构建起一个基于 Docker 原生的集群系统。

> 当然首先你要懂 Docker 不要拿他当虚拟机。

**交流QQ群：**454201740

------------------
###最新版本:
 - `20170508`
   - 界面显示优化。
   - 加强了各类资源信息的展示。
   - 增加了更多的服务和容器的创建选项。
   - 对于用时长的操作采用了异步的形式。
   - 修复容器日志限制排版混乱的问题。
   - 修复容器日志显示重复内容的问题。
   - 更新 JDocker 和 voovan 到最新版本。

 - `20170227` 首个发布版本

 请在 pull 的时候,替换版本号<version>。

------------------

####feature:
#####自发布之日起,得到了各位朋友的支持,也给我了更多的信心,我会持续支持本项目的开发。
#####有新的建议大家可以用 feature 标签 提交 issues,我会根据功能的相互依赖性以及其使用的广泛型,编排版本.

 - 权限管理，每个用户只能管理自己创建的容器、服务、卷、网络等资源。
 - 通过 swarm 服务节点管理所有加入该服务节点的容器、卷等等资源。
 


------------------
###使用方法
 - Clone 后在本地使用
```shell
git clone https://git.oschina.net/helyho/DockerFly.git
cd DockerFly
./start.sh
```
    > `start.sh` 脚本依赖 socat,所以请您先安装 socat 到您的系统中,或者您已经暴露了一个 docker 的 TCP 端口,请删除对应的命令。并访问:http://127.0.0.1:28083 在 setting 页面配置您的 ip 和 端口。

    > `Ubuntu安装socat`: shell sudo apt-get install socat

    > `Centos安装socat`: shell yum install -y socat

 - 直接 pull 一个 dockerfly 容器
```shell
    docker pull helyho/dockerfly[:<version>]
    docker run \
            --name dockerfly -d \
            -v /var/run/docker.sock:/var/run/docker.sock \
            -p 28083:28083 \
    dockerfly
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