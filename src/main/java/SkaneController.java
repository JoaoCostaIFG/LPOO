import room.element.Skane;

public class SkaneController {
    private Skane ska;
    private Integer max_oxy;

    public SkaneController(Skane ska, Integer max_oxygen) {
        this.ska = ska;
        this.max_oxy = max_oxygen;
    }

    public void toggleBury() {
        if (ska.isBury())
            ska.bury(false);
        else if (ska.getOxygenLevel().equals(max_oxy))
            ska.bury(true);
    }

    public void inhale() {
        Integer oxy_lvl = ska.getOxygenLevel();
        if (oxy_lvl.equals(0))
            ska.bury(false);

        if (ska.isBury()) {
            ska.setOxygenLevel(oxy_lvl - 1);
        } else if (oxy_lvl < max_oxy) {
            if (oxy_lvl + max_oxy / 50 > max_oxy)
                ska.setOxygenLevel(max_oxy);
            else
                ska.setOxygenLevel(oxy_lvl + max_oxy / 50);
        }
    }

    public void takeDamage(Integer dmg) {
        ska.takeDamage(dmg);
        for (int i = 0; i < dmg; ++i)
            ska.shrink();
    }

    public void nom(Integer nourishment) {
        ska.setHp(ska.getHp() + nourishment);
        for (int i = 0; i < nourishment; ++i)
            ska.grow();
    }
}
