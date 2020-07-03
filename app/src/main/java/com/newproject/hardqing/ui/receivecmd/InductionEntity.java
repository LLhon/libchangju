package com.newproject.hardqing.ui.receivecmd;

public class InductionEntity {
    /**
     * {
     *     "cmd":"open_room_introduction" //方法名
     * }
     */
    private String cmd;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "InductionEntity{" +
                "cmd='" + cmd + '\'' +
                '}';
    }
}
