public class SinhVien extends Nguoi{
    private String maSV;
    private String lop;

    public SinhVien(String maSV, String hoTen, String lop, String diaChi) {
        super(hoTen, diaChi);
        this.maSV = maSV;
        this.lop = lop;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }
}
