import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents a finite language.
 *
 * @author Dr. Jody Paul
 * @author Naji Shamas
 * @version 1.3.1
 */
public final class Language implements Iterable<String>, java.io.Serializable {
    /** The empty string. */
    private static final String EMPTY_STRING = "";
    /** The empty set. */
    private static final Set<String> EMPTY_SET = new TreeSet<String>();

    /** The set of strings in this language, initially empty. */
    private Set<String> strings;

    /**
     * Create a language with no strings.
     */
    public Language() {
        strings = new TreeSet<String>(); //instantiate empty set of strings
    }

    /**
     * Indicates if this language has no strings.
     * @return true if the language is equivalent to the empty set;
     *         false otherwise
     */
    public boolean isEmpty() {
        //here methods from the Set class will be invoked.
        return (strings.isEmpty()) ? true : false; 
    }

    /**
     * Accesses the number of strings (cardinality) in this language.
     * @return the cardinality of the language
     */
    public int cardinality() {
        //here we invoke size() method to return cardinality.
        return (strings.size() > Integer.MAX_VALUE) ? Integer.MAX_VALUE : strings.size();
    }

    /**
     * Determines if a specified string is in this language.
     * @param candidate the string to check
     * @return true if the string is in the language,
     *         false if not in the language or the parameter is null
     */
    public boolean includes(final String candidate) {
        //Here we invoke contains() method from Set class.
        return strings.contains(candidate) ? true : false;
    }

    /**
     * Ensures that this language includes the given string
     * (adds it if necessary).
     * @param memberString the string to be included in the language
     * @return true if this language changed as a result of the call
     */
    public boolean addString(final String memberString) {
        int temp = strings.size(); //set a temp variable to size of string set before updating
        strings.add(memberString);
        return (strings.size() > temp) ? true : false; //check if cardinality has changed.
        
    }

    /**
     * Ensures that this language includes all of the strings
     * in the given parameter (adds any as necessary).
     * @param memberStrings the strings to be included in the language
     * @return true if this language changed as a result of the call
     */
    public boolean addAllStrings(final Collection<String> memberStrings) {
        //invoking addAll() method from Set class here.
        int temp = strings.size();
        strings.addAll(memberStrings);
        return (strings.size() > temp) ? true : false;
    }

    /**
     * Provides an iterator over the strings in this language.
     * @return an iterator over the strings in this language in ascending order
     */
    public Iterator<String> iterator() {
        //creating Iterator instance by invoking iterator() method on strings set.
        Iterator<String> iter = strings.iterator();
        return iter;
    }

    /**
     * Creates a language that is the concatenation of this language
     * with another language.
     * @param language the language to be concatenated to this language
     * @return the concatenation of this language with the parameter language
     */
    public Language concatenate(final Language language) {
        Language concat_lang;
        concat_lang = new Language();
        List<String> string_list = new ArrayList<String>(strings); //creating some string lists for easier concatenation as I am relatively new to Java.
        List<String> string_list2 = new ArrayList<String>(language.strings);
        for(int i = 0; i < strings.size(); i++)
        {
            var x = string_list.get(i);
            for(int j = 0; j < language.strings.size(); j++)
            {
                var y = string_list2.get(j);
                if(y != "" && x != "")
                {
                    concat_lang.strings.add(x+y);
                }
                else if(y == "")
                {
                    concat_lang.strings.add(x);
                }
                else if(x == "")
                {
                    concat_lang.strings.add(y);
                }   
            }
        
        }

        return concat_lang;
    }

    @Override
    public boolean equals(final Object obj) {
        Language lang_obj = Language.class.cast(obj);
        if (obj instanceof Language) {
            
        }
        return strings.equals(lang_obj.strings);
    }
    @Override
    public int hashCode() {
        return (int) strings.hashCode();
    }
}

