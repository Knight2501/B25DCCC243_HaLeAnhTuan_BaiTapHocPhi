import java.util.*;

abstract class SinhVien {
    protected static final long DON_GIA = 450000L;
    protected String maSV;
    protected String maHe;
    protected int soTinChi;

    public SinhVien(String maHe, String maSV, int soTinChi) {
        this.maHe = maHe;
        this.maSV = maSV;
        this.soTinChi = soTinChi;
    }

    public abstract long hocPhi();

    @Override
    public String toString() {
        return maSV + " " + maHe + " " + hocPhi();
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
        for (int i = 0; i < n; i++) {
            String[] p = sc.nextLine().trim().split("\\s+");
            String he = p[0], ma = p[1];
            int tc = Integer.parseInt(p[2]);
            switch (he) {
                case "CQ":  ds.add(new ChinhQuy(ma, tc)); break;
                case "CLC": ds.add(new ChatLuongCao(ma, tc)); break;
                case "LT":  ds.add(new LienThong(ma, tc)); break;
            }
        }
        for (SinhVien sv : ds) System.out.println(sv);
    }
}