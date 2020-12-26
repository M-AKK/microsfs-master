package net.wenz.service.fs.service;

import java.io.File;
import java.io.IOException;

public interface StubService {
    boolean put(String path, String name, File file) throws IOException;
    File get(String path) throws IOException;
    void delete(String uuid) throws IOException;
}
