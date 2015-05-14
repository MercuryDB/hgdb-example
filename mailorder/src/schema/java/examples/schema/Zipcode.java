package examples.schema;

import com.github.mercurydb.annotations.HgValue;

@SuppressWarnings("unused")
public class Zipcode {
    private int zip;
    private String city;

    public Zipcode(int z, String c) {
        zip = z;
        city = c;
    }

    @HgValue("zip")
    public int getZip() {
        return zip;
    }

    @HgValue("city")
    public String getCity() {
        return city;
    }

    public String toString() {
        return Utilities.formatTuple("" + zip, city);
    }
}
