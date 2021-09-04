package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String ex_name = "";
    private int cal_burnt = -1;
    private int ex_hours = -1;

    public Exercise() {
        this.ex_name = "";
        this.cal_burnt = -1;
        this.ex_hours = -1;
    }

    public Exercise(String ex_name, int cal_burnt, int ex_hours) {
        this.ex_name = ex_name;
        this.cal_burnt = cal_burnt;
        this.ex_hours = ex_hours;
    }

    public String getEx_name() {
        return ex_name;
    }

    public void setEx_name(String ex_name) {
        this.ex_name = ex_name;
    }

    public int getCal_burnt() {
        return cal_burnt;
    }

    public void setCal_burnt(int cal_burnt) {
        this.cal_burnt = cal_burnt;
    }

    public int getEx_hours() {
        return ex_hours;
    }

    public void setEx_hours(int ex_hours) {
        this.ex_hours = ex_hours;
    }
}
