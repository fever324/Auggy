package ResourceHelper;

import java.util.Collection;
import java.util.HashMap;

import com.example.android.navigationdrawerexample.R;

public class IconHelper {
	private static HashMap<String,Integer> icons = new HashMap<String,Integer>();
	
	public static int getIcon(String icon){
		if(icons.size() == 0){
			init();
		}
		return icons.get(icon);
	}
	
	public static Collection<Integer> getAllIconIDs(){
		if(icons.size() == 0){
			init();
		}
		return icons.values();
	}
	
	public static Collection<String> getAllIcons(){
		if(icons.size() == 0){
			init();
		}
		return icons.keySet();
	}
	
	private static void init(){
		icons.put("chicken", R.drawable.chicken);
		icons.put("beef", R.drawable.beef);
		icons.put("seafood", R.drawable.seafood);
		icons.put("fish", R.drawable.fish);
		icons.put("vegi", R.drawable.vegi);
		icons.put("peanut", R.drawable.peanut);
		icons.put("pork", R.drawable.pork);
	}
	
	
	
}
