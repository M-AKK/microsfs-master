package net.wenz.service.fs.model.vo;

import net.wenz.service.fs.constant.Constant;
import net.wenz.service.fs.constant.FileType;
import net.wenz.service.fs.exception.DirectoryDontEmptyException;
import net.wenz.service.fs.exception.FileTreeNodeNullException;
import net.wenz.service.fs.model.dao.FileDao;
import net.wenz.service.fs.model.entity.FileBlock;
import net.wenz.service.fs.model.entity.FileEntity;
import net.wenz.service.fs.exception.PathException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Component
public class FileTree {

    @Autowired
    FileDao fileDao;

    private FileTreeNode rootNode;
    private Map<String, FileTreeNode> filePathCache;

    /*
       name: initFileTree
       auth: caowenzhi
       desc: init file tree and path cache, first init root node, then init 1st layer nodes
     */
    public void initFileTree() throws PathException {
        // init file path cache
        filePathCache = new HashMap<String, FileTreeNode>();

        // init root node 设置根节点
        FileEntity entity = fileDao.getFileById(Constant.ROOT_UUID);
        FileTreeNode root = new FileTreeNode(entity);
        this.rootNode = root;
        this.filePathCache.put(Constant.FILE_ROOT, root);

        // init 1st layer nodes 初始化子节点
        List<FileEntity> children = fileDao.getFilesInDirectory(entity.getId());
        for (FileEntity ent : children) {
            FileTreeNode child = new FileTreeNode(ent, root);
            root.putChildNode(child);
            this.filePathCache.put(child.getAbstractPath(), child);
        }
    }

    /*
       name: _normalPath
       auth: caowenzhi
       desc: splite path and file name
     */
    private String _normalPath(String path) throws PathException {
        if (path.equals(Constant.FILE_ROOT))
            return path;

        if (!path.startsWith(Constant.FILE_ROOT)) {
            throw new PathException(String.format("File path must start with '%s'", Constant.FILE_ROOT));
        }

        // remove last "/"
        if (path.endsWith(Constant.FILE_SEPARATE))
            path = path.substring(0, path.length() - 1);

        return path;
    }

    /*
       name: _splitePathName
       auth: caowenzhi
       desc: splite path and file name
     */
    private String[] _splitePathName(String path) {
        int idx = path.lastIndexOf(Constant.FILE_SEPARATE) + 1;
        String[] ret = {path.substring(0, idx), path.substring(idx)};
        return ret;
    }

    /*
       name: getFilerOrDirectory
       auth: caowenzhi
       desc: init file tree and path cache, first init root node, then init 1st layer nodes
     */
    public FileTreeNode getFilerOrDirectory(String path) throws PathException, FileTreeNodeNullException {
        // get file node from cache
        if (this.filePathCache.containsKey(path))
            return this.filePathCache.get(path);

        // get file node from DB
        String _path = this._normalPath(path);

        String[] nodenames = _path.split(Constant.FILE_SEPARATE);
        int i = 0;
        FileTreeNode current = this.rootNode;
        String cachepath = Constant.FILE_ROOT;
        while (i < nodenames.length) {
            // ignore null node
            if (nodenames[i].equals("")) {
                i++;
                continue;
            }

            // get the children node from db if parent node did not have it
            if (!current.hasChildNode(nodenames[i])) {
                List<FileEntity> children = fileDao.getFilesInDirectory(current.getFileEntity().getId());
                for (FileEntity ent : children) {
                    if (!current.hasChildNode(ent.getAlias())) {
                        FileTreeNode child = new FileTreeNode(ent, current);
                        current.putChildNode(child);

                        this.filePathCache.put(cachepath + (cachepath.endsWith(Constant.FILE_SEPARATE) ? "" : Constant.FILE_SEPARATE) + ent.getAlias(), child);
                    }
                }
            }

            // get node
            current = current.getChildNode(nodenames[i]);
            if (current == null) {
                throw new FileTreeNodeNullException(String.format("File node '%s' is null in %s", nodenames[i], path));
            }
            // update path cache and index
            cachepath += (cachepath.endsWith(Constant.FILE_SEPARATE) ? "" : Constant.FILE_SEPARATE) + current.getFileEntity().getAlias();
            this.filePathCache.put(cachepath, current);
            i++;
        }
        return current;
    }

    /*
       name: listDirectory
       auth: caowenzhi
       desc: list directory,
     */
    public Collection<FileTreeNode> listDirectory(String path) throws PathException, FileTreeNodeNullException {
        path = this._normalPath(path);//调整下格式

        // get file node from cache or db
        FileTreeNode fnode = null;
        if (this.filePathCache.containsKey(path))
            fnode = filePathCache.get(path);
        else
            fnode = this.getFilerOrDirectory(path);

        //#TODO campare size and children num
        List<FileEntity> children = fileDao.getFilesInDirectory(fnode.getFileEntity().getId());
        for (FileEntity ent : children) {
            if (!fnode.hasChildNode(ent.getAlias())) {
                FileTreeNode child = new FileTreeNode(ent, fnode);
                fnode.putChildNode(child);

                this.filePathCache.put(path + Constant.FILE_SEPARATE + ent.getAlias(), child);
            }
        }
        return fnode.getAllChildNodes();
    }

    /*
       name: addDirectory
       auth: caowenzhi
       desc: Add Directory to file tree
     */
    public void addDirectory(String path, String name) throws FileTreeNodeNullException, PathException {
        String path0 = path+"/"+name;
        System.out.println("测试="+path0);
        path = this._normalPath(path0);

        String[] _path = this._splitePathName(path);
        // get parent node
        FileTreeNode parent = this.getFilerOrDirectory(_path[0]);

        // create file entity
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

    /*
      name: addFile
      auth: caowenzhi
      desc: Add File to file tree
    */
    public String addFile(String path,String name) throws FileTreeNodeNullException, PathException {
        if (path.endsWith(Constant.FILE_SEPARATE))
            throw new PathException(String.format("File path donot should end with '%s'", Constant.FILE_SEPARATE));

        String[] _path = this._splitePathName(path);
        // get parent node
        FileTreeNode parent = this.getFilerOrDirectory(_path[0]);

        // create file entity
        FileEntity ent = new FileEntity();
        ent.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        ent.setName(name);
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

    public void removeDirectory(String path) throws FileTreeNodeNullException, PathException, DirectoryDontEmptyException {
        FileTreeNode dir = this.getFilerOrDirectory(path);
        // remove dir only if it is empty
        if (dir.childrenSize() != 0)
            throw new DirectoryDontEmptyException(String.format("Directory '%s' is not empty", path));

        // remove from db
        fileDao.removeFileEntity(dir.getFileEntity().getId());
        // remove from cache
        this.filePathCache.remove(path);
        dir.getParentNode().removeChildNode(dir.getFileEntity().getAlias());
    }

    public void removeFile(String path) throws FileTreeNodeNullException, PathException {
        FileTreeNode file = this.getFilerOrDirectory(path);

        fileDao.removeFileEntity(file.getFileEntity().getId());
        file.getParentNode().removeChildNode(file.getFileEntity().getAlias());
    }
}