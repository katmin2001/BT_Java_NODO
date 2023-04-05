public class DangKy {
    private String ngayDK;
    private SinhVien sinhVien;
    private MonHoc monHoc;

    public DangKy(String ngayDK, SinhVien sinhVien, MonHoc monHoc) {
        this.ngayDK = ngayDK;
        this.sinhVien = sinhVien;
        this.monHoc = monHoc;
    }

    public String getNgayDK() {
        return ngayDK;
    }

    public void setNgayDK(String ngayDK) {
        this.ngayDK = ngayDK;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public MonHoc getMonHoc() {
        return monHoc;
    }

    public void setMonHoc(MonHoc monHoc) {
        this.monHoc = monHoc;
    }
}
