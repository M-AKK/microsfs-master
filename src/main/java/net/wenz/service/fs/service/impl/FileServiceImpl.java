package net.wenz.service.fs.service.impl;

import net.wenz.service.fs.config.ApplicationCache;
import net.wenz.service.fs.config.FileTree;
import net.wenz.service.fs.model.dao.DataNodeDao;
import net.wenz.service.fs.model.dao.FileDao;
import net.wenz.service.fs.model.entity.DataNode;
import net.wenz.service.fs.model.entity.FileBlock;
import net.wenz.service.fs.model.entity.FileDuplicate;
import net.wenz.service.fs.model.vo.BlockInfo;
import net.wenz.service.fs.model.vo.FileTreeNode;
import net.wenz.service.fs.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FileServiceImpl implements FileService {

    @Value("${blockSzie}")
    private long blockSzie;

    @Value("${duplicateNumber}")
    private int duplicateNumber;

    @Autowired
    ApplicationCache applicationCache;

    @Autowired
    FileDao fileDao;

    @Autowired
    DataNodeDao dataNodeDao;

    @Override
    public void mkdir(String path, String name) {
        FileTree fs = applicationCache.getFileTree();
        fs.addDirectory(path);
    }

    @Override
    public void rmdir(String path) {
        FileTree fs = applicationCache.getFileTree();
        fs.removeDirectory(path);
    }

    @Override
    public Collection<FileTreeNode> ls(String path) {
        FileTree fs = applicationCache.getFileTree();
        FileTreeNode node = fs.getFile(path);
        return node.getAllChildNodes();
    }

    @Override
    public List<BlockInfo> put(String path, long size) {
        FileTree fs = applicationCache.getFileTree();
        String fid = fs.addFile(path);

        long num = size / blockSzie + 1;
        List<BlockInfo> ret = new ArrayList<BlockInfo>();

        for (int i=0; i<num; i++) {
            FileBlock block = new FileBlock();
            String bid = UUID.randomUUID().toString().replaceAll("-", "");
            block.setId(bid);
            block.setFileId(fid);
            block.setSequence(i+1);
            long _size = blockSzie;
            if (i+1 == num) {
                _size = size - i * blockSzie;
            }
            block.setSize(_size);
            fileDao.insertFileBlock(block);

            block.setDuplicates(new ArrayList<>());
            for (int j=0; j<duplicateNumber; j++) {
                FileDuplicate dup = new FileDuplicate();
                dup.setBlockId(bid);
                String did = UUID.randomUUID().toString().replaceAll("-", "");
                dup.setId(did);
                fileDao.insertFileDuplicate(dup);
                block.getDuplicates().add(dup);

                BlockInfo blockvo = new BlockInfo();
                blockvo.setBid(bid);
                blockvo.setId(did);
                blockvo.setPath(path);
                blockvo.setSeq(i+1);
                blockvo.setSize(_size);
                List<DataNode> nodes = dataNodeDao.getAllDataNode();
                Random random = new Random();
                int idx = random.nextInt(nodes.size());
                blockvo.setDataNode(nodes.get(idx));
                ret.add(blockvo);
            }
        }
        return ret;
    }

    @Override
    public void ackput(String path, String bid, String id, String mcode) {
        fileDao.updateDuplicateMcode(id, mcode);

        FileTree fs = applicationCache.getFileTree();
        FileTreeNode node = fs.getFile(path);
        List<FileBlock> blocks = node.getFileEntity().getBlocks();
        for (FileBlock b : blocks) {
            if (b.getId().equals(bid)) {
                List<FileDuplicate> dups = b.getDuplicates();
                for (FileDuplicate d : dups) {
                    if (d.getId().equals(id)) {
                        DataNode dn = dataNodeDao.getDataNodeById(mcode);
                        d.setDataNode(dn);
                    }
                }
            }
        }
    }
}
