package controller;

import room.element.skane.Skane;

public class SkaneController {
    private Skane ska;
    private int maxOxy;
    private int scentDur;

    public SkaneController(Skane ska, int scentDur) {
        this.ska = ska;
        this.maxOxy = ska.getMaxOxygenLevel();
        this.scentDur = scentDur;
    }

    public void takeDamage(int dmg) {
        if (dmg < 0) return;
        ska.takeDamage(dmg);
        for (int i = 0; i < dmg; ++i)
            ska.shrink();
    }

    public void nom(int nourishment) {
        if (nourishment < 0) return;
        ska.setHp(ska.getHp() + nourishment);
        for (int i = 0; i < nourishment; ++i)
            ska.grow();
    }

    public void toggleBury() {
        if (ska.isBury())
            ska.bury(false);
        else if (ska.getOxygenLevel() == maxOxy)
            ska.bury(true);
    }

    public void inhale() {
        int oxy_lvl = ska.getOxygenLevel();
        if (oxy_lvl == 0)
            ska.bury(false);

        if (ska.isBury())
            ska.setOxygenLevel(oxy_lvl - 1);
        else if (oxy_lvl < maxOxy)
            ska.setOxygenLevel(Math.min(oxy_lvl + 2, maxOxy));
    }

    public void tickScentTrail() {
        ska.tickScentTrail();
        ska.dropScent(scentDur);
    }
}
