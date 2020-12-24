package net.wenz.service.fs.model.vo;

import net.wenz.service.fs.constant.Constant;
import net.wenz.service.fs.exception.PathException;
import net.wenz.service.fs.model.entity.FileEntity;

import java.util.*;

public class FileTreeNode {

    private FileEntity fileEntity;
    private FileTreeNode parentNode;



    private String path;
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

    public String getAbstractPath() throws PathException {
        if (this.parentNode == null && this.fileEntity.getName().equals(Constant.FILE_ROOT) )
            return this.fileEntity.getName();

        String ppath = this.parentNode.getAbstractPath();
        if (ppath == null)
            throw new PathException(String.format("File path '%s' is error", ppath));
        return ppath + Constant.FILE_SEPARATE + this.fileEntity.getName();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
