package net.wenz.service.fs.service;

import net.wenz.service.fs.model.vo.BlockInfo;
import net.wenz.service.fs.model.vo.FileTreeNode;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface StubService {
    boolean put(String path, File file) throws IOException;
    File get(String path) throws IOException;
}
