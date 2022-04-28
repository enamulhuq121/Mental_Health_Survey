package barcode;

import java.io.Serializable;

/**
 * Created by Guice on 1/18/2018.
 */

public class New_NID implements Serializable {

    private String name;
    private String nw;
    private String ol;
    private String br;
    private String pe;
    private String pr;
    private String va;
    private String dt;
    private String pk;
    private String sg;

    public New_NID() {
    }

    public New_NID(String name, String nw, String ol, String br, String pe, String pr, String va, String dt, String pk, String sg) {
        this.name = name;
        this.nw = nw;
        this.ol = ol;
        this.br = br;
        this.pe = pe;
        this.pr = pr;
        this.va = va;
        this.dt = dt;
        this.pk = pk;
        this.sg = sg;
    }

    @Override
    public String toString() {
        return "New_NID{" +
                "name='" + name + '\'' +
                ", nw='" + nw + '\'' +
                ", ol='" + ol + '\'' +
                ", br='" + br + '\'' +
                ", pe='" + pe + '\'' +
                ", pr='" + pr + '\'' +
                ", va='" + va + '\'' +
                ", dt='" + dt + '\'' +
                ", pk='" + pk + '\'' +
                ", sg='" + sg + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNw() {
        return nw;
    }

    public void setNw(String nw) {
        this.nw = nw;
    }

    public String getOl() {
        return ol;
    }

    public void setOl(String ol) {
        this.ol = ol;
    }

    public String getBr() {
        return br;
    }

    public void setBr(String br) {
        this.br = br;
    }

    public String getPe() {
        return pe;
    }

    public void setPe(String pe) {
        this.pe = pe;
    }

    public String getPr() {
        return pr;
    }

    public void setPr(String pr) {
        this.pr = pr;
    }

    public String getVa() {
        return va;
    }

    public void setVa(String va) {
        this.va = va;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getSg() {
        return sg;
    }

    public void setSg(String sg) {
        this.sg = sg;
    }
}
