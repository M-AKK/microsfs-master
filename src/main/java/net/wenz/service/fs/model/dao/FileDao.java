package net.wenz.service.fs.model.dao;

import net.wenz.service.fs.model.entity.FileBlock;
import net.wenz.service.fs.model.entity.FileDuplicate;
import net.wenz.service.fs.model.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileDao {
    List<FileEntity> getFilesInDirectory(@Param("parent") String parent);//查找父节点下的子节点
    FileEntity getFileById(@Param("id") String id);
    FileBlock getBlockByFid(@Param("fid") String fid);
    FileBlock getBlockById(@Param("id") String id);

    int insertFileEntity(FileEntity fileEntity);
    int insertFileBlock(FileBlock fileBlock);
    int insertFileDuplicate(FileDuplicate fileDuplicate);

    void updateDuplicateMcode(@Param("id") String id, @Param("mcode") String mcode);

    void removeFileEntity(@Param("id") String id);
    void removeFileBlock(@Param("id") String id);
    void removeFileDuplicate(@Param("id") String id);
}
