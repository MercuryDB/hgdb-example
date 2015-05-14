package examples.schema;

import com.github.mercurydb.annotations.HgValue;

@SuppressWarnings("unused")
public class Part {
    private int pno;
    private String pname;
    private int qoh; // quantity on hand
    private double price;
    private int olevel;

    public Part(int part, String name, int qty, double price, int level) {
        this.pno = part;
        this.pname = name;
        this.qoh = qty;
        this.price = price;
        this.olevel = level;
    }

    @HgValue("pno")
    public int getPno() {
        return pno;
    }

    @HgValue("pname")
    public String getPname() {
        return pname;
    }

    @HgValue("qoh")
    public int getQoh() {
        return qoh;
    }

    @HgValue("price")
    public double getPrice() {
        return price;
    }

    @HgValue("olevel")
    public int getOlevel() {
        return olevel;
    }

    public String toString() {
        return Utilities.formatTuple("" + pno, pname, "" + qoh, "" + price, "" + olevel);
    }
}
