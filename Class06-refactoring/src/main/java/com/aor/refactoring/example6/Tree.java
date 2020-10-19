package com.aor.refactoring.example6;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Tree {
    private Date planted_at;
    private List<Date> appraisal_dates;

    private Location loc;

    public Tree(Date planted_at, Location loc) {
        this.planted_at = planted_at;
        this.loc = loc;
        this.appraisal_dates = new ArrayList<>();
    }

    public String toString() {
        return "Tree planted at " + planted_at.toString() + " in location " + loc.toString();
    }

    public void setLocation(Location new_loc) {
        this.loc = new_loc;
    }

    public Location getLocation() {
        return loc;
    }

    public Date getPlantDate() {
        return this.planted_at;
    }

    void addAppraisal(Date appraisalDate) {
        this.appraisal_dates.add(appraisalDate);
    }

    public List<Date> getAppraisals() {
        return this.appraisal_dates;
    }

    public Date getLatestAppraisalDate() {
        Date today = new Date();
        Date latest_appraisal = appraisal_dates.get(0);

        for (Date appraisal : this.appraisal_dates)
            if (appraisal.after(latest_appraisal))
                latest_appraisal = appraisal;

        return latest_appraisal;
    }

    public boolean isNextAppraisalOverdue() {
        if (appraisal_dates.size() == 0)
            return false;

        Date latest_appraisal = getLatestAppraisalDate();
        // Calculate next appraisal date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(latest_appraisal);
        calendar.add(Calendar.MONTH, 3);

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
            calendar.add(Calendar.DAY_OF_MONTH, 2);
        else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        Date nextAppraisalDate = calendar.getTime();
        // Appraisal is only overdue if its date is in the past
        return nextAppraisalDate.before(new Date());
    }
}
