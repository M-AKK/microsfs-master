package net.wenz.service.fs.model.vo;

import net.wenz.service.fs.model.entity.FileEntity;

import java.util.*;

public class FileTreeNode {

    private FileEntity fileEntity;
    private FileTreeNode parentNode;

    private Map<String, FileTreeNode> childrenNodes;

    public FileTreeNode(FileEntity fileEntity, FileTreeNode parentNode) {
        this.fileEntity = fileEntity;
        this.parentNode = parentNode;

        this.childrenNodes = new HashMap<String, FileTreeNode>();
    }

    public FileTreeNode(FileEntity fileEntity) {
        this.fileEntity = fileEntity;
        this.parentNode = null;

        this.childrenNodes = new HashMap<String, FileTreeNode>();
    }

    public FileEntity getFileEntity() {
        return fileEntity;
    }

    public void setFileEntity(FileEntity fileEntity) {
        this.fileEntity = fileEntity;
    }

    public FileTreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(FileTreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public void putChildNode(FileTreeNode childNode) {
        this.childrenNodes.put(childNode.fileEntity.getAlias(), childNode);
    }

    public FileTreeNode getChildNode(String name) {
        return this.childrenNodes.get(name);
    }

    public boolean hasChildNode(String alias) {
        return this.childrenNodes.containsKey(alias);
    }

    public int childrenSize() {
        return this.childrenNodes.size();
    }

    public void removeChildNode(String alias) {
        this.childrenNodes.remove(alias);
    }

    public Collection<FileTreeNode> getAllChildNodes() {
        return this.childrenNodes.values();
    }
}
