package net.wenz.service.fs.service.impl;

import net.wenz.service.fs.model.dao.DuplicateDao;
import net.wenz.service.fs.model.entity.FileDuplicate;
import net.wenz.service.fs.service.DuplicateService;
import org.springframework.beans.factory.annotation.Autowired;

public class DuplicateServiceImpl implements DuplicateService {

    @Autowired
    DuplicateDao duplicateDao;

    @Override
    public FileDuplicate getDuplicateByBid(String bid) {
        FileDuplicate fileDuplicate = duplicateDao.getFileDuplicateByBid(bid);
        return fileDuplicate;
    }
}
