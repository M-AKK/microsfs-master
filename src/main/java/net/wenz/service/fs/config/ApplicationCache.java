package net.wenz.service.fs.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ApplicationCache {

    @Autowired
    private FileTree fileTree;

    public FileTree getFileTree() {
        return this.fileTree;
    }

}
