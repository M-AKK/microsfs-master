package net.wenz.service.fs.config;

import net.wenz.service.fs.exception.PathException;
import net.wenz.service.fs.model.dao.FileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitFileTree implements ApplicationListener<ContextRefreshedEvent> {

    private static final long serialVersionUID = 1L;
    // private static final String classPrifix = "com.hope.tbdc.util.constant.";

    @Autowired
    ApplicationCache applicationCache;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            applicationCache.getFileTree().initFileTree();
        } catch (PathException e) {
            e.printStackTrace();
        }
    }
}
