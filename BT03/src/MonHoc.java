public class MonHoc {
    private String maMon;
    private String tenMon;
    private int soDvht;

    public MonHoc(String maMon, String tenMon, int soDvht) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.soDvht = soDvht;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getSoDvht() {
        return soDvht;
    }

    public void setSoDvht(int soDvht) {
        this.soDvht = soDvht;
    }
}
