package examples.schema;

import com.github.mercurydb.annotations.HgIndexStyle;
import com.github.mercurydb.annotations.HgUpdate;
import com.github.mercurydb.annotations.HgValue;

@SuppressWarnings("unused")
public class Odetail {
    private int ono;
    private int pno;
    private int qty;

    // TODO add multiple part constructor
    public Odetail(int ono, int p, int q) {
        this.ono = ono;
        this.pno = p;
        this.qty = q;
    }

    @HgValue(value = "ono", index = HgIndexStyle.ORDERED)
    public int getOno() {
        return ono;
    }

    @HgValue(value = "pno", index = HgIndexStyle.UNORDERED)
    public int getPnos() {
        return pno;
    }

    @HgUpdate("pno")
    public void addPart(int p) {
        this.pno = p;
    }

    @HgValue("qty")
    public int getQuantity() {
        return qty;
    }

    public String toString() {
        return Utilities.formatTuple("" + ono, "" + pno, "" + qty);
    }
}
