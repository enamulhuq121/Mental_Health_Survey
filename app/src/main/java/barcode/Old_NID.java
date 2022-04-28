package barcode;

import java.io.Serializable;

/**
 * Created by Guice on 1/21/2018.
 */

public class Old_NID implements Serializable {
    private String pin;
    private String name;
    private String DOB;
    private String FP;
    private String F;
    private String TYPE;
    private String V;
    private String ds;

    @Override
    public String toString() {
        return "Old_NID{" +
                "pin='" + pin + '\'' +
                ", name='" + name + '\'' +
                ", DOB='" + DOB + '\'' +
                ", FP='" + FP + '\'' +
                ", F='" + F + '\'' +
                ", TYPE='" + TYPE + '\'' +
                ", V='" + V + '\'' +
                ", ds='" + ds + '\'' +
                '}';
    }

    public Old_NID(String pin, String name, String DOB, String FP, String f, String TYPE, String v, String ds) {
        this.pin = pin;
        this.name = name;
        this.DOB = DOB;
        this.FP = FP;
        F = f;
        this.TYPE = TYPE;
        V = v;
        this.ds = ds;
    }

    public Old_NID()
    {

    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getFP() {
        return FP;
    }

    public void setFP(String FP) {
        this.FP = FP;
    }

    public String getF() {
        return F;
    }

    public void setF(String f) {
        F = f;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getV() {
        return V;
    }

    public void setV(String v) {
        V = v;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }
}


