package pers.xiaofeng.daqiaobugong;

/**
 * @description：用户麻将操作类
 * @author：晓峰
 * @date：2020/9/3/10:14
 */
public class UserOpt {

    private int deskId;             // 桌子id（前两位 0：普通房间 1：金币场 2：比赛场）
    private int gameId;             // 游戏id
    private int opt;                // 操作码
    private int parameter;          // 操作码参数
    private int module;             // 对用用户服处理方式

    public int getDeskId() {
        return deskId;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getOpt() {
        return opt;
    }

    public void setOpt(int opt) {
        this.opt = opt;
    }

    public int getParameter() {
        return parameter;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }

    public int getModule() {
        return module;
    }

    public void setModule(int module) {
        this.module = module;
    }
}
