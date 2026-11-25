import java.util.*;

interface MienGiam {
    long tinhGiam(long hocPhi);
}

class ConChinhSach implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) { return hocPhi * 50 / 100; }
}

class HoNgheo implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) { return hocPhi * 70 / 100; }
}

class HocBong implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) { return Math.min(3000000L, hocPhi); }
}

abstract class SinhVien {
    protected static final long DON_GIA = 450000L;
    protected String maSV;
    protected String maHe;
    protected int soTinChi;
    protected MienGiam mienGiam;

    public SinhVien(String maHe, String maSV, int soTinChi) {
        this.maHe = maHe;
        this.maSV = maSV;
        this.soTinChi = soTinChi;
    }

    public String getMaSV() { return maSV; }
    public String getMaHe() { return maHe; }
    public void setMienGiam(MienGiam mg) { this.mienGiam = mg; }

    public abstract long hocPhi();

    public long soTienGiam() {
        return mienGiam == null ? 0 : mienGiam.tinhGiam(hocPhi());
    }

    public long phaiNop() { return hocPhi() - soTienGiam(); }

    @Override
    public String toString() {
        return maSV + " " + maHe + " " + hocPhi() + " " + soTienGiam() + " " + phaiNop();
    }
}

class ChinhQuy extends SinhVien {
    public ChinhQuy(String maSV, int tc) { super("CQ", maSV, tc); }

    @Override
    public long hocPhi() { return soTinChi * DON_GIA; }
}

class ChatLuongCao extends SinhVien {
    public ChatLuongCao(String maSV, int tc) { super("CLC", maSV, tc); }

    @Override
    public long hocPhi() { return soTinChi * DON_GIA * 3 / 2; }
}

class LienThong extends SinhVien {
    public LienThong(String maSV, int tc) { super("LT", maSV, tc); }

    @Override
    public long hocPhi() { return soTinChi * DON_GIA + 2000000L; }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());
        List<SinhVien> ds = new ArrayList<>();
        Map<String, SinhVien> theoMa = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String[] p = sc.nextLine().trim().split("\\s+");
            int tc = Integer.parseInt(p[2]);
            SinhVien sv;
            switch (p[0]) {
                case "CQ":  sv = new ChinhQuy(p[1], tc); break;
                case "CLC": sv = new ChatLuongCao(p[1], tc); break;
                default:    sv = new LienThong(p[1], tc); break;
            }
            ds.add(sv);
            theoMa.put(p[1], sv);
        }

        int m = Integer.parseInt(sc.nextLine().trim());
        List<String> notFound = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            String[] p = sc.nextLine().trim().split("\\s+");
            SinhVien sv = theoMa.get(p[0]);
            if (sv == null) {
                notFound.add(p[0]);
                continue;
            }
            switch (p[1]) {
                case "CS": sv.setMienGiam(new ConChinhSach()); break;
                case "HN": sv.setMienGiam(new HoNgheo()); break;
                case "HB": sv.setMienGiam(new HocBong()); break;
            }
        }

        for (String ma : notFound) System.out.println("NOT FOUND " + ma);
        for (SinhVien sv : ds) System.out.println(sv);

        String[] he = {"CQ", "CLC", "LT"};
        for (String h : he) {
            int dem = 0;
            long tong = 0;
            for (SinhVien sv : ds) {
                if (sv.getMaHe().equals(h)) {
                    dem++;
                    tong += sv.phaiNop();
                }
            }
            if (dem > 0) System.out.println(h + " " + dem + " " + tong);
        }
    }
}