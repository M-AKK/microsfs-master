package net.wenz.service.fs.service.impl;

import net.wenz.service.fs.model.dao.DataNodeDao;
import net.wenz.service.fs.model.entity.DataNode;
import net.wenz.service.fs.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NodeServiceImpl implements NodeService {

    @Autowired
    DataNodeDao dataNodeDao;

    @Override
    public void register(String mcode, String ip, long port) {
        DataNode node = new DataNode();
        node.setMachineCode(mcode);
        node.setIp(ip);
        node.setPort(port);
        node.setRegisterTime(new Date());
        node.setActiveTime(new Date());

        dataNodeDao.insertDataNode(node);
    }

    @Override
    public void active(String mcode) {
        dataNodeDao.updateActiveTime(mcode, new Date());
    }

    @Override
    public void unregister(String mcode) {
        dataNodeDao.removeDataNode(mcode);
    }

    @Override
    public List<DataNode> listNodes() {
        List<DataNode> ret = dataNodeDao.getAllDataNode();
        return ret;
    }

    @Override
    public DataNode getNode(String mcode) {
        DataNode ret = dataNodeDao.getDataNodeById(mcode);
        return ret;
    }

    @Override
    public void updateAddress(String mcode, String ip, long port) {
        dataNodeDao.updateAddress(mcode, ip, port);
    }
}
