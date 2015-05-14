package examples.schema;

import com.github.mercurydb.annotations.HgIndexStyle;
import com.github.mercurydb.annotations.HgUpdate;
import com.github.mercurydb.annotations.HgValue;

@SuppressWarnings("unused")
public class Order {
    private int ono;
    private int cno;
    private int eno;
    private String received; // Date
    private String shipped; // Date

    public Order(int o, int c, int e, String r, String s) {
        ono = o;
        cno = c;
        eno = e;
        received = r;
        shipped = s;
    }

    @HgValue(value = "ono", index = HgIndexStyle.ORDERED)
    public int getOno() {
        return ono;
    }

    @HgUpdate("ono")
    public void setOno(int o) {
        if (o < 0) {
            this.ono = -1; // detect bad order numbers with a single error number
            return;
        }

        this.ono = o;
    }

    public String toString() {
        return Utilities.formatTuple("" + ono, "" + cno, "" + eno, received, shipped);
    }
}
