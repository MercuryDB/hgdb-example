package examples.schema;

import com.github.mercurydb.annotations.HgValue;

@SuppressWarnings("unused")
public class Employee {
    private int eno;
    private String ename;
    private int zip;
    private String date;

    public Employee(int e, String n, int z, String d) {
        eno = e;
        ename = n;
        zip = z;
        date = d;
    }

    @HgValue("eno")
    public int getEno() {
        return eno;
    }

    @HgValue("name")
    public String getName() {
        return ename;
    }

    @HgValue("zipcode")
    public int getZipcode() {
        return zip;
    }

    @HgValue("date")
    public String getDate() {
        return date;
    }

    public String toString() {
        return Utilities.formatTuple("" + eno, ename, "" + zip, date);
    }
}
