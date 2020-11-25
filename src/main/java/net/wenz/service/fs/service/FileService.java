package net.wenz.service.fs.service;

import net.wenz.service.fs.model.entity.FileBlock;
import net.wenz.service.fs.model.vo.BlockInfo;
import net.wenz.service.fs.model.vo.FileTreeNode;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

public interface FileService {
    void mkdir(String path, String name) ;
    void rmdir(String path) ;

    Collection<FileTreeNode> ls(String path) ;

    List<BlockInfo> put(String path, long size);
    void ackput(String path, String bid, String id, String mcode);
}
