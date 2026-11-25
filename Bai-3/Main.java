import java.util.*;

class DuLieuKhongHopLeException extends Exception {
    public DuLieuKhongHopLeException(String message) {
        super(message);
    }
}

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

    public String getMaSV() { return maSV; }

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

class QuanLyHocPhi {
    private ArrayList<SinhVien> ds = new ArrayList<>();

    // Ném ngoại lệ nếu dòng không hợp lệ
    public void them(String maHe, String maSV, String tcStr) throws DuLieuKhongHopLeException {
        if (!maHe.equals("CQ") && !maHe.equals("CLC") && !maHe.equals("LT"))
            throw new DuLieuKhongHopLeException("Ma he khong hop le");

        int tc;
        try {
            tc = Integer.parseInt(tcStr);
        } catch (NumberFormatException e) {
            throw new DuLieuKhongHopLeException("So tin chi khong phai so nguyen");
        }
        if (tc < 1 || tc > 30)
            throw new DuLieuKhongHopLeException("So tin chi ngoai doan [1, 30]");

        for (SinhVien sv : ds) {
            if (sv.getMaSV().equals(maSV))
                throw new DuLieuKhongHopLeException("Trung ma sinh vien");
        }

        switch (maHe) {
            case "CQ":  ds.add(new ChinhQuy(maSV, tc)); break;
            case "CLC": ds.add(new ChatLuongCao(maSV, tc)); break;
            default:    ds.add(new LienThong(maSV, tc)); break;
        }
    }

    public void sapXep() {
        ds.sort((a, b) -> {
            int c = Long.compare(b.hocPhi(), a.hocPhi());       
            if (c != 0) return c;
            return a.getMaSV().compareTo(b.getMaSV());            
        });
    }

    public void inDanhSach() {
        for (SinhVien sv : ds) System.out.println(sv);
    }

    public long tongHocPhi() {
        long t = 0;
        for (SinhVien sv : ds) t += sv.hocPhi();
        return t;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());
        QuanLyHocPhi ql = new QuanLyHocPhi();
        for (int i = 0; i < n; i++) {
            String[] p = sc.nextLine().trim().split("\\s+");
            try {
                ql.them(p[0], p[1], p[2]);
            } catch (DuLieuKhongHopLeException e) {
                System.out.println("INVALID " + p[1]);
            }
        }
        ql.sapXep();
        ql.inDanhSach();
        System.out.println("TONG " + ql.tongHocPhi());
    }
}