package net.wenz.service.fs.model.entity;

import net.wenz.service.fs.constant.FileType;

import java.util.Date;

public class FileDuplicate {
    private String id;
    private String blockId;
    private String machinecode;

    private DataNode dataNode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public DataNode getDataNode() {
        return dataNode;
    }

    public void setDataNode(DataNode dataNode) {
        this.dataNode = dataNode;
    }

    public String getMachinecode() {
        return machinecode;
    }

    public void setMachinecode(String machinecode) {
        this.machinecode = machinecode;
    }
}
