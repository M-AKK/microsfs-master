package net.wenz.service.fs.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

//import org.apache.log4j.Logger;

public class DefaultTypeStrategy implements ExclusionStrategy {
//    Logger logger = Logger.getLogger(this.getClass());

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

}
