package net.wenz.service.fs.config;

import net.wenz.service.fs.constant.Constant;
import net.wenz.service.fs.constant.FileType;
import net.wenz.service.fs.model.dao.FileDao;
import net.wenz.service.fs.model.entity.FileBlock;
import net.wenz.service.fs.model.entity.FileEntity;
import net.wenz.service.fs.model.vo.FileTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FileTree {

    @Autowired
    FileDao fileDao;

    private FileTreeNode rootNode;
    private Map<String, FileTreeNode> filePathCache;

//    public FileTree() {
//        filePathCache = new HashMap<String, FileTreeNode>();
//    }

    public void initFileTree() {
        filePathCache = new HashMap<String, FileTreeNode>();
        FileEntity entity = fileDao.getFileById(Constant.ROOT_UUID);
        FileTreeNode root = new FileTreeNode(entity);
        List<FileEntity> children = fileDao.getFilesInDirectory(entity.getId());
        for (FileEntity ent : children) {
            FileTreeNode child = new FileTreeNode(ent, root);
            root.putChildNode(child);
        }
        this.rootNode = root;
        this.filePathCache.put("/", root);
    }

    public FileTreeNode getFile(String path) {
        if (this.filePathCache.containsKey(path))
            return this.filePathCache.get(path);

        String[] nodenames = path.split("/");
        int i = 0;
        FileTreeNode current = this.rootNode;
        String cachepath = "/";
        while (i < nodenames.length) {
            if (nodenames[i].equals("")) {
                i++;
                continue;
            }

            if (!current.hasChildNode(nodenames[i])) {
                List<FileEntity> children = fileDao.getFilesInDirectory(current.getFileEntity().getId());
                for (FileEntity ent : children) {
                    if (!current.hasChildNode(ent.getAlias())) {
                        FileTreeNode child = new FileTreeNode(ent, current);
                        current.putChildNode(child);

                        this.filePathCache.put(cachepath + (cachepath.endsWith("/") ? "" : "/") + ent.getAlias(), child);
                    }
                }
            }
            current = current.getChildNode(nodenames[i]);
            if (current == null)
                break;

            cachepath += (cachepath.endsWith("/") ? "" : "/") + current.getFileEntity().getAlias();
            this.filePathCache.put(cachepath, current);
            i++;
        }
        return current;
    }

    private String[] _splitePathName(String path) {
        int idx = path.lastIndexOf("/") + 1;
        String[] ret = {path.substring(0, idx), path.substring(idx)};
        return ret;
    }

    public void addDirectory(String path) {
        String[] _path = this._splitePathName(path);
        FileTreeNode parent = this.getFile(_path[0]);

        if (_path[1].equals(""))
            return;

        FileEntity ent = new FileEntity();
        ent.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        ent.setName(_path[1]);
        ent.setAlias(_path[1]);
        ent.setFileType(FileType.DIRECTORY);
        ent.setParentId(parent.getFileEntity().getId());
        ent.setCreateTime(new Date());
        ent.setModifyTime(new Date());

        fileDao.insertFileEntity(ent);
        FileTreeNode child = new FileTreeNode(ent, parent);
        parent.putChildNode(child);

        this.filePathCache.put(path, child);
    }

    public String addFile(String path) {
        String[] _path = this._splitePathName(path);
        FileTreeNode parent = this.getFile(_path[0]);

        if (_path[1].equals(""))
            return null;

        FileEntity ent = new FileEntity();
        ent.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        ent.setName(_path[1]);
        ent.setAlias(_path[1]);
        ent.setFileType(FileType.FILE);
        ent.setParentId(parent.getFileEntity().getId());
        ent.setCreateTime(new Date());
        ent.setModifyTime(new Date());
        ent.setBlocks(new ArrayList<FileBlock>());

        fileDao.insertFileEntity(ent);
        FileTreeNode child = new FileTreeNode(ent, parent);
        parent.putChildNode(child);

        this.filePathCache.put(path, child);
        return ent.getId();
    }

    public void removeDirectory(String path) {
        FileTreeNode dir = this.getFile(path);
        if (dir.childrenSize() != 0)
            return;

        fileDao.removeFileEntity(dir.getFileEntity().getId());
        dir.getParentNode().removeChildNode(dir.getFileEntity().getAlias());
    }
}