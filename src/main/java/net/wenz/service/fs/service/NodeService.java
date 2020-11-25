package net.wenz.service.fs.service;

import net.wenz.service.fs.model.entity.DataNode;
import net.wenz.service.fs.model.vo.FileTreeNode;

import java.util.Collection;
import java.util.List;

public interface NodeService {
    void register(String mcode, String ip, long port) ;
    void active(String mcode) ;
    void unregister(String mcode);

    List<DataNode> listNodes();
    DataNode getNode(String mcode);

    void updateAddress(String mcode, String ip, long port);
}
