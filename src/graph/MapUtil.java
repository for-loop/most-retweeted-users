package graph;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Collections;

/** Utility class for Map object
 * This class is courtesy of
 * @author Carter Page, customized by Hiro Watari
 * https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values
 */
public class MapUtil {
	
	/**
	 * Sorts Map by value
	 * @param map An unsorted map object
	 * @return A map object sorted by value
	 */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean desc) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());
        
        if (desc == true)
        	// make the list in descending order
        	Collections.reverse(list);

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}