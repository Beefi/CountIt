package ph.edu.ph.mobdeve.s18.baliog.miguel.countit.model;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String dateTaken = "";
    private String ex_name = "";
    private double cal_burnt = 0;

    public Exercise() {
        this.dateTaken = "";
        this.ex_name = "";
        this.cal_burnt = 0;
    }

    public Exercise(String ex_name, double cal_burnt) {
        this.dateTaken = "";
        this.ex_name = ex_name;
        this.cal_burnt = cal_burnt;
    }

    public Exercise(String dateTaken, String ex_name, double cal_burnt) {
        this.dateTaken = dateTaken;
        this.ex_name = ex_name;
        this.cal_burnt = cal_burnt;
    }

    public String getEx_name() {
        return ex_name;
    }

    public void setEx_name(String ex_name) {
        this.ex_name = ex_name;
    }

    public double getCal_burnt() {
        return cal_burnt;
    }

    public void setCal_burnt(double cal_burnt) {
        this.cal_burnt = cal_burnt;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }
}
