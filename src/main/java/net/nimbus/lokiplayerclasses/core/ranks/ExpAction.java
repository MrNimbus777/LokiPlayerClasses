package net.nimbus.lokiplayerclasses.core.ranks;

public enum ExpAction {
    KILL_PLAYER(100),
    KILL_MOB(25),
    KILL_BOSS(100),
    KILL_RARE_BOSS(150),
    KILL_ANIMAL(10)
    ;

    final int exp;
    ExpAction(int exp) {
        this.exp = exp;
    }
    public int getExp() {
        return exp;
    }
}
