/**
 * Created by helyho on 2017/5/4.
 */
//=================== List ===================
function getContainers(){
    doImport("org.voovan.docker.command.Container.CmdContainerList")
    try {

        var cmdContainerList = new CmdContainerList();
        connect(cmdContainerList);
        cmdContainerList.all(true)
        var containerList = cmdContainerList.send().sortBy("id", true);
        cmdContainerList.close();
        cmdContainerList.release();
        return containerList;
    } catch (e) {
        alertError(e)
    }
}

function getServices(serviceId){
    doImport("org.voovan.docker.command.Service.CmdServiceList")
    try {
        var cmdServiceList = new CmdServiceList();
        if(serviceId!=null){
            cmdServiceList.id([serviceId])
        }
        connect(cmdServiceList);
        var serviceList = cmdServiceList.send().sortBy("id", true);

        cmdServiceList.close();
        cmdServiceList.release();
        return serviceList;
    } catch (e) {
        alertError(e)
    }
}

function getTaskList(serviceId){
    doImport("org.voovan.docker.command.Task.CmdTaskList")
    try {
        var cmdTaskList = new CmdTaskList();
        if(serviceId!=null) {
            cmdTaskList.service(serviceId);
        }
        connect(cmdTaskList);
        var taskList = cmdTaskList.send().sortBy("name");
        cmdTaskList.close();
        cmdTaskList.release();
        return taskList;
    } catch (e) {
        alertError(e)
    }
}

function getNodes(){
    doImport("org.voovan.docker.command.Node.CmdNodeList")
    try {

        var cmdNodeList = new CmdNodeList();
        connect(cmdNodeList);
        //cmdContainerList.label("author","helyho");
        var nodeList = cmdNodeList.send().sortBy("name");
        cmdNodeList.close();
        cmdNodeList.release();
        return nodeList;
    } catch (e) {
        alertError(e)
    }
}

function getNetworks(){
    doImport("org.voovan.docker.command.Network.CmdNetworkList")
    try {
        var cmdNetworkList = new CmdNetworkList();
        connect(cmdNetworkList);
        var networkList = cmdNetworkList.send().sortBy("name");
        cmdNetworkList.close();
        cmdNetworkList.release();
        return networkList;
    } catch (e) {
        alertError(e)
    }
}

function getImages(){
    doImport("org.voovan.docker.command.Image.CmdImageList")
    try {
        var cmdImageList = new CmdImageList();
        connect(cmdImageList);
        var imageList = cmdImageList.send().sortBy("Created");
        cmdImageList.close();
        cmdImageList.release();
        return imageList;
    } catch (e) {
        alertError(e)
    }
}

function getVolumes(){
    doImport("org.voovan.docker.command.Volume.CmdVolumeList")
    try {
        var cmdVolumeList = new CmdVolumeList();
        connect(cmdVolumeList)
        var volumeList = cmdVolumeList.send().sortBy("name");
        cmdVolumeList.close();
        cmdVolumeList.release();
        return volumeList;
    } catch (e) {
        alertError(e)
    }
}


//=================== Detail ===================
function getImageDetail(idOrName){
    doImport("org.voovan.docker.command.Image.CmdImageDetail")

    try {
        var cmdImageDetail = new CmdImageDetail(idOrName)
        connect(cmdImageDetail)
        var viewImage = cmdImageDetail.send();
        cmdImageDetail.close();
        cmdImageDetail.release();

        if (viewImage != null) {
            return viewImage;
        } else {
            return null;
        }
    } catch (e) {
        alertError(e)
    }
}

function getContainerDetail(idOrName){
    doImport("org.voovan.docker.command.Container.CmdContainerDetail")
    try {

        var cmdContainerDetail = new CmdContainerDetail(idOrName)
        connect(cmdContainerDetail)
        var viewContainer = cmdContainerDetail.send();
        cmdContainerDetail.close();
        cmdContainerDetail.release();
        if(viewContainer.hostConfig.cpuPeriod!=0) {
            viewContainer.hostConfig.cpu = viewContainer.hostConfig.cpuQuota / viewContainer.hostConfig.cpuPeriod
        }else{
            viewContainer.hostConfig.cpu = 0;
        }

        return viewContainer;

    } catch (e) {
        alertError(e)
    }
}

function getNetworkDetail(idOrName){
    doImport("org.voovan.docker.command.Network.CmdNetworkDetail")
    try {
        var cmdNetworkDetail = new CmdNetworkDetail(idOrName)
        connect(cmdNetworkDetail)
        var viewNetwork = cmdNetworkDetail.send();
        cmdNetworkDetail.close();
        cmdNetworkDetail.release();

        return viewNetwork;
    } catch (e) {
        alertError(e)
    }
}

function getVolumeDetail(idOrName){
    doImport("org.voovan.docker.command.Volume.CmdVolumeDetail")
    try {
        var cmdVolumeDetail = new CmdVolumeDetail(idOrName)
        connect(cmdVolumeDetail)
        var viewVolume = cmdVolumeDetail.send();
        cmdVolumeDetail.close();
        cmdVolumeDetail.release();
        return viewVolume;
    } catch (e) {
        alertError(e)
    }
}

//=================== Map ===================
function getContainersIdAndName(){
    var containers = [];
    var containerList = getContainers();
    for(var i=0;i<containerList.length;i++){
        var container = containerList[i];
        containers[container.id] = container.names[0];
    }
    return containers;
}

function getServicesIdAndName(){
    var services = [];
    var serviceList = getServices();
    for(var i=0;i<serviceList.length;i++){
        var service = serviceList[i];
        services[service.id] = service.spec.name;
    }
    return services;
}

function getNodesIdAndName(){
    var nodes = [];
    var nodeList = getNodes();
    for(var i=0;i<nodeList.length;i++){
        var node = nodeList[i];
        var node = nodeList[i];
        nodes[node.id] = {};
        nodes[node.id].name =node.spec.name;
        nodes[node.id].hostname = node.description.hostname;
    }
    return nodes;
}

function getNetworksIdAndName(){
    try {
        var networks = [];
        var networkList = getNetworks();
        for(var i=0;i<networkList.length;i++){
            var network = networkList[i];
            networks[network.id] = network.name;
        }
        return networks;
    } catch (e) {
        alertError(e)
    }
}