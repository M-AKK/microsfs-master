package net.wenz.service.fs;

public class Test {
    static public void main(String[] args) {
        String path = "/wenz/jsx/";
        String spath = path.substring(0, path.length()-1);
        System.out.println(spath);
//        int idx = path.lastIndexOf("/");
//        String[] ret = {path.substring(0, idx+1), path.substring(idx+1)};
//        System.out.println(ret[0]);
//        System.out.println(ret[1]);
    }
}
