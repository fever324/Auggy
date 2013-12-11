package ResourceHelper;

import java.util.HashMap;

import com.example.android.navigationdrawerexample.R;

public class IconHelper {
	public static HashMap<String,Integer> icons = new HashMap<String,Integer>();
	
	public static int getIcon(String icon){
		if(icons.size() == 0){
			init();
		}
		return icons.get(icon);
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
