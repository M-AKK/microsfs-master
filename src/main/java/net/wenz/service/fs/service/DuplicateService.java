package net.wenz.service.fs.service;

import net.wenz.service.fs.model.entity.FileDuplicate;

public interface DuplicateService {
    FileDuplicate getDuplicateByBid(String bid);
    FileDuplicate getFileDuplicateByMachineCode(String machinecode);
}
