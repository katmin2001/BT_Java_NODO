import org.apache.commons.math3.analysis.function.Sinh;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<SinhVien> danhSachSV = new ArrayList<>();
    public static ArrayList<MonHoc> danhSachMH = new ArrayList<>();
    public static ArrayList<DangKy> danhSachDK = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        fileSV("SV.xlsx");
        fileMH("MONHOC.xlsx");
        fileDK("DANGKY.xlsx");
        int choice;
        do {
            System.out.println("MENU");
            System.out.println("1. Nhập n sinh viên");
            System.out.println("2. Nhập m môn học");
            System.out.println("3. Nhập danh sách đăng ký");
            System.out.println("4. In danh sách đăng ký");
            System.out.println("5. Sắp xếp danh sách đăng ký");
            System.out.println("0. Thoát");
            System.out.print("Lua chon: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    nhapDanhSachSinhVien();
                    break;
                case 2:
                    nhapDanhSachMonHoc();
                    break;
                case 3:
                    nhapDanhSachDangKy();
                    break;
                case 4:
                    inDanhSachDangKy();
                    break;
                case 5:
                    sapXepDanhSachDangKy();
                    break;
                case 0:
                    System.out.println("Chương trình kết thúc");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại!");
                    break;
            }
        } while (choice != 0);
    }
    public static boolean isValidDate(String input) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        try {
            format.parse(input);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    private static void sapXepDanhSachDangKy() {
        int chon;
        do {
            System.out.println("Lựa chọn tiêu chí sắp xếp");
            System.out.println("1. Sắp xếp theo họ tên sinh viên");
            System.out.println("2. Sắp xếp theo tên môn học");
            System.out.println("3. In danh sách đăng ký");
            System.out.println("0. Quay lại");
            System.out.print("Lua chon: ");
            chon = scanner.nextInt();
            scanner.nextLine();

            switch (chon) {
                case 1:
                    sortByName();
                    break;
                case 2:
                    sortBySub();
                    break;
                case 3:
                    inDanhSachDangKy();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại!");
                    break;
            }
        } while (chon != 0);
    }

    private static void sortByName() {
        Collections.sort(danhSachDK, new Comparator<DangKy>() {
            @Override
            public int compare(DangKy o1, DangKy o2) {
                return o1.getSinhVien().getHoTen().compareToIgnoreCase(o2.getSinhVien().getHoTen());
            }
        });
    }
    private static void sortBySub() {
        Collections.sort(danhSachDK, new Comparator<DangKy>() {
            @Override
            public int compare(DangKy o1, DangKy o2) {
                return o1.getMonHoc().getTenMon().compareToIgnoreCase(o2.getMonHoc().getTenMon());
            }
        });
    }
    private static void inDanhSachDangKy() {
        System.out.printf("%-20s %-20s %-20s %-20s %-20s%n","Ma SV","Ten SV","Ma MH","Ten MH","Ngay DK");
        for(DangKy dangKy: danhSachDK){
            System.out.printf("%-20s %-20s %-20s %-20s %-20s%n",dangKy.getSinhVien().getMaSV(),dangKy.getSinhVien().getHoTen(),dangKy.getMonHoc().getMaMon(),dangKy.getMonHoc().getTenMon(),dangKy.getNgayDK());
        }
    }
    private static boolean limitRegister(String maSV){
        int dem = 0;
        for (DangKy dangKy: danhSachDK){
            if(dangKy.getSinhVien().getMaSV().equals(maSV)){
                dem++;
            }
        }
        if (dem < 5){
            return true;
        }
        return false;
    }
    private static void nhapDanhSachDangKy() throws Exception {
        while(true){
            System.out.print("Nhap ma sinh vien can dang ky: ");
            String maSV = scanner.nextLine();
            if(limitRegister(maSV)==true){
                if (existIDSV(maSV)==false){
                    System.out.println("Sinh vien khong ton tai!! Moi nhap lai!!");
                }else {
                    while(true){
                        System.out.print("Nhap ma mon hoc can dang ky: ");
                        String maMH = scanner.nextLine();
                        if(existIDMH(maMH)==false){
                            System.out.println("Mon hoc khong ton tai!! Moi nhap lai!!");
                        }else {
                            while (true){
                                System.out.print("Ngay dang ky: ");
                                String ngayDK = scanner.nextLine();
                                if(isValidDate(ngayDK)==true){
                                    SinhVien sv = findSV(maSV);
                                    MonHoc mh = findMH(maMH);
                                    DangKy dangKy = new DangKy(ngayDK, sv,mh);
                                    danhSachDK.add(dangKy);
                                    break;
                                }
                                else{
                                    System.out.println("Ngay thang khong dung dinh dang! Moi nhap lai!");
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
            }else {
                System.out.println("Sinh vien da dang ky du mon hoc! (Gioi han 5 mon)");
            }
        }
        RegisterToFile(danhSachDK);
    }

    private static void nhapDanhSachMonHoc() throws Exception {
        System.out.print("Nhap so mon hoc can them: ");
        int n = Integer.parseInt(scanner.nextLine());
        for(int i = 0; i < n; i++){
            while(true){
                System.out.println("Nhap mon hoc "+(i+1)+":");
                System.out.print("Ma mon: ");
                String maMon = scanner.nextLine();
                System.out.print("Ten mon: ");
                String tenMon = scanner.nextLine();
                System.out.print("So dvht: ");
                int soDvht = Integer.parseInt(scanner.nextLine());

                MonHoc monHoc = new MonHoc(maMon,tenMon,soDvht);
                if(existIDMH(monHoc.getMaMon())==false){
                    danhSachMH.add(monHoc);
                    break;
                }
                else {
                    System.out.println("Mon hoc da ton tai! Moi nhap lai!");
                }
            }

        }
        writeMHToFile(danhSachMH);
        System.out.println("Danh sach mon hoc:");
        System.out.printf("%-20s %-20s %-20s%n","Ma mon","Ten mon","So dvht");
        for(MonHoc monHoc: danhSachMH){
            System.out.printf("%-20s %-20s %-20d%n",monHoc.getMaMon(),monHoc.getTenMon(),monHoc.getSoDvht());
        }
    }

    private static void nhapDanhSachSinhVien() throws Exception {
        System.out.print("Nhap so sinh vien can them: ");
        int n = Integer.parseInt(scanner.nextLine());
        for(int i = 0; i < n; i++){
            while(true){
                System.out.println("Nhap sinh vien "+(i+1)+":");
                System.out.print("Ho va Ten: ");
                String hoTen = scanner.nextLine();
                System.out.print("Dia chi: ");
                String diaChi = scanner.nextLine();
                System.out.print("Ma sinh vien: ");
                String maSV = scanner.nextLine();
                System.out.print("Lop: ");
                String lop = scanner.nextLine();

                SinhVien sinhVien = new SinhVien(maSV,hoTen,lop,diaChi);
                if(existIDSV(sinhVien.getMaSV())==false){
                    danhSachSV.add(sinhVien);
                    break;
                }
                else {
                    System.out.println("Sinh vien da ton tai! Moi nhap lai!");
                }
            }

        }
        writeStudentsToFile(danhSachSV);
        System.out.println("Danh sach sinh vien:");
        System.out.printf("%-20s %-10s %-20s %-20s%n","Ho Ten","Ma SV","Lop","Dia chi");
        for(SinhVien sinhVien: danhSachSV){
            System.out.printf("%-20s %-10s %-20s %-20s%n",sinhVien.getHoTen(),sinhVien.getMaSV(),sinhVien.getLop(),sinhVien.getDiaChi());
        }
    }
    public static void RegisterToFile(ArrayList<DangKy> dangKIES) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Đăng ký");

        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Mã sinh viên");
        row.createCell(1).setCellValue("Họ tên");
        row.createCell(2).setCellValue("Mã môn học");
        row.createCell(3).setCellValue("Tên môn học");
        row.createCell(4).setCellValue("Ngày đăng ký");

        for(DangKy dangKy: dangKIES) {
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(dangKy.getSinhVien().getMaSV());
            row.createCell(1).setCellValue(dangKy.getSinhVien().getHoTen());
            row.createCell(2).setCellValue(dangKy.getMonHoc().getMaMon());
            row.createCell(3).setCellValue(dangKy.getMonHoc().getTenMon());
            row.createCell(4).setCellValue(dangKy.getNgayDK());
        }

        FileOutputStream outputStream = new FileOutputStream("DANGKY.xlsx");
        workbook.write(outputStream);
        workbook.close();
    }
    public static void writeStudentsToFile(ArrayList<SinhVien> sinhViens) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sinh viên");

        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Họ tên");
        row.createCell(1).setCellValue("Địa chỉ");
        row.createCell(2).setCellValue("Mã sinh viên");
        row.createCell(3).setCellValue("Lớp");

        for(SinhVien sinhVien: sinhViens) {
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(sinhVien.getHoTen());
            row.createCell(1).setCellValue(sinhVien.getDiaChi());
            row.createCell(2).setCellValue(sinhVien.getMaSV());
            row.createCell(3).setCellValue(sinhVien.getLop());
        }

        FileOutputStream outputStream = new FileOutputStream("SV.xlsx");
        workbook.write(outputStream);
        workbook.close();
    }
    public static void writeMHToFile(ArrayList<MonHoc> monHocs) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Môn học");

        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Mã MH");
        row.createCell(1).setCellValue("Tên MH");
        row.createCell(2).setCellValue("Số đvht");

        for(MonHoc monHoc:monHocs) {
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(monHoc.getMaMon());
            row.createCell(1).setCellValue(monHoc.getTenMon());
            row.createCell(2).setCellValue(monHoc.getSoDvht());
        }

        FileOutputStream outputStream = new FileOutputStream("MONHOC.xlsx");
        workbook.write(outputStream);
        workbook.close();
    }
    public static boolean existIDSV(String maSV){
        for (SinhVien sinhVien: danhSachSV){
            if(sinhVien.getMaSV().equals(maSV)){
                return true;
            }
        }
        return false;
    }
    public static boolean existIDMH(String maMH){
        for (MonHoc monHoc: danhSachMH){
            if(monHoc.getMaMon().equals(maMH)){
                return true;
            }
        }
        return false;
    }
    public static SinhVien findSV(String id){
        for(SinhVien sinhVien: danhSachSV){
            if(sinhVien.getMaSV().equals(id)){
                return new SinhVien(sinhVien.getMaSV(),sinhVien.getHoTen(),sinhVien.getLop(),sinhVien.getDiaChi());
            }
        }
        return null;
    }
    public static MonHoc findMH(String id){
        for(MonHoc monHoc: danhSachMH){
            if(monHoc.getMaMon().equals(id)){
                return new MonHoc(monHoc.getMaMon(), monHoc.getTenMon(),monHoc.getSoDvht());
            }
        }
        return null;
    }
    public static void fileSV(String path){
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String hoTen = row.getCell(0).getStringCellValue();
                String diaChi = row.getCell(1).getStringCellValue();
                String maSV = row.getCell(2).getStringCellValue();
                String lop = row.getCell(3).getStringCellValue();
                SinhVien sinhVien = new SinhVien(maSV,hoTen,lop,diaChi);
                danhSachSV.add(sinhVien);
            }

            workbook.close();
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void fileMH(String path){
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String maMH = row.getCell(0).getStringCellValue();
                String tenMH = row.getCell(1).getStringCellValue();
                int soDvht = (int) row.getCell(2).getNumericCellValue();
                MonHoc monHoc = new MonHoc(maMH,tenMH,soDvht);
                danhSachMH.add(monHoc);
            }

            workbook.close();
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void fileDK(String path){
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String maSV = row.getCell(0).getStringCellValue();
                String hoTen = row.getCell(1).getStringCellValue();
                String maMH = row.getCell(2).getStringCellValue();
                String tenMH = row.getCell(3).getStringCellValue();
                String ngayDK = row.getCell(4).getStringCellValue();
                SinhVien sv = findSV(maSV);
                MonHoc mh = findMH(maMH);
                danhSachDK.add(new DangKy(ngayDK,sv,mh));
            }

            workbook.close();
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}