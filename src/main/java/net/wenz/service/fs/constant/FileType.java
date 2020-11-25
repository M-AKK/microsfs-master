package net.wenz.service.fs.constant;

public enum FileType {
    NONE(0, "未知"), FILE(1, "文件"), DIRECTORY(2, "目录");

    private int value;
    private String show;

    private FileType(int value, String show) {
        this.value = value;
        this.show = show;
    }

    public int getValue() {
        return value;
    }

    public String getShow() {
        return show;
    }

    public boolean equal(int obj) {
        return this.value == obj;
    }

    public boolean equal(String obj) {
        return this.show == obj;
    }

    public String toString() {
        return this.show + "[" + this.value + "]";
    }

    public static FileType getFileType(int tid) {
        for (FileType account : FileType.values()) {
            if (account.value == tid)
                return account;
        }
        return null;
    }
}