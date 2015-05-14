package examples.schema;

/**
 * <p>
 * We find it useful to define a class for utility methods.
 * In the spirit of a relational database this is kept purely
 * separate from the definitions of the other classes in this
 * package which represent only tables of data.
 * </p>
 * <p>
 * We document that this class is <code>final</code> because
 * the definition of this class does not lend itself to ever have
 * values which would be stored in a table. It is simply utility code.
 * Furthermore we do not desire to ever have another type extend
 * this type. Thus we expect that no corresponding Table class will
 * be generated from this code. Marking this class as <code>final</code>
 * and declaring no <code>@HgValue</code>s makes it possible for
 * MercuryDB to determine that no Table class should be generated for
 * this class, as it does not represent a possible table schema.
 * </p>
 * <p>
 * If a class is intended for consumption within this package only,
 * it also is best practice not to declare it as public.
 * As of 0.6.2 it is not possible to mark a top-level class in
 * a schema package as anything other than public because a table
 * will be generated for all top-level classes in the schema.
 * </p>
 */
public final class Utilities {
    // TODO remove public modifier from this class once bug has been fixed.
    static String formatTuple(Object... item) {
        String result = "(";
        String next = ", ";
        for (Object s : item) {
            result = result + s.toString() + next;
        }
        return result + ")";
    }
}
