
/*
Specify an unindexed value called "name" which
represents the result of getName (a String).
*/
@HgValue("name")
public String getName() { /* ... */ }

/*
Specify a value called "ono" with an ORDERED index, which
represents the result of getOrderNumber (an int).
*/
@HgValue(value = "ono", index = HgIndexStyle.ORDERED)
public int getOrderNumber() { /* ... */ }

/*
Specify a value called "pnos" with an UNORDERED index,
which represents the result of getPartNumbers (a List<Part>).
*/
@HgValue(value = "pnos", index = HgIndexStyle.UNORDERED)
public List<Part> getPartNumbers() { /* ... */ }




In order to have an ORDERED index the return type needs to have a natural order, either by being a primitive type or by implementing the Comparable<T> interface.

Note that it is possible to have Collections as values. We have implemented logic to deal with Collections as first-class database value types in the ways which make sense for Collections. 

In the example above, there might not be any useful concept of ordering for these Collection values, and we probably only care about equality, so we will use an UNORDERED index which will be faster for equality queries and joins, but which sacrifices any kind of speed for inequality-based operations.








/*
Specify that setName() will update the value named "name".
*/
@HgUpdate("name")
public void setName(String str) { /* ... */ }

/*
Specify that baz() will update the values named "foo" and "bar".
You can specify any number of values.
*/
@HgUpdate({"foo", "bar"})
public void baz() { /* ... */ }



These methods could update some internal state of the object which is not of the same type as the value it updates, but that's okay because all we care about is that the result of calling this method is that the value has changed, and that value will be automatically updated in any applicable indexes. Because the actual operation of this method is not our concern, we can allow any number of parameters of any type and a return value of any type for any method marked `@HgUpdate`.

It is important that the developer take note that they should not mark a method as an update for a particular value if it CANNOT cause that named value to be updated because this will introduce inefficiencies into the program in the form of forcing additional unnecessary updates to any applicable indexes.