package net.wenz.service.fs.model.dao;

import net.wenz.service.fs.model.entity.DataNode;
import net.wenz.service.fs.model.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface DataNodeDao {
    List<DataNode> getAllDataNode();
    DataNode getDataNodeById(@Param("mcode") String mcode);

    int insertDataNode(DataNode dataNode);
    void removeDataNode(@Param("id") String id);

    void updateActiveTime(@Param("mcode") String mcode, @Param("date") Date date);

    void updateAddress(@Param("mcode") String mcode, @Param("ip") String ip, @Param("port") long port);
}
