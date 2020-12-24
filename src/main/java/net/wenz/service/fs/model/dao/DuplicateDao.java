package net.wenz.service.fs.model.dao;

import net.wenz.service.fs.model.entity.FileDuplicate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DuplicateDao {
    FileDuplicate getFileDuplicateByBid(@Param("bid") String bid);
    void removeDuplicate(@Param("id") String id);
    FileDuplicate getFileDuplicateByMachineCode(@Param("machinecode") String machinecode);
}
