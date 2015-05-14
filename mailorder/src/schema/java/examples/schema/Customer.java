package examples.schema;

import com.github.mercurydb.annotations.HgIndexStyle;
import com.github.mercurydb.annotations.HgUpdate;
import com.github.mercurydb.annotations.HgValue;

/**
 * Even though
 */
@SuppressWarnings("unused")
public class Customer {
    private int cno;
    private String cname;
    private String street;
    private int zip;
    private String phone;

    public Customer(int c, String n, String s, int z, String p) {
        cno = c;
        cname = n;
        street = s;
        zip = z;
        phone = p;
    }

    public String toString() {
        return Utilities.formatTuple("" + cno, cname, street, "" + zip, phone);
    }

    @HgValue(value = "cno")
    public int getCno() {
        return cno;
    }

    @HgValue(value = "cname", index = HgIndexStyle.ORDERED)
    public String getName() {
        return cname;
    }

    @HgUpdate("cname")
    public void setName(String name) {
        this.cname = name;
    }

    @HgValue(value = "street", index = HgIndexStyle.ORDERED)
    public String getStreet() {
        return street;
    }

    @HgValue(value = "zipcode")
    public int getZipcode() {
        return zip;
    }

    @HgValue(value = "phone")
    public String getPhone() {
        return phone;
    }
}
