package utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONProcessor {
	public static Hashtable<String, Field[]> fieldHash= new Hashtable<String, Field[]>();
	public static JSONArray generateJSONArray(ArrayList<?> objList)
	{
		JSONArray jsonArray = new JSONArray();
		Class<?> cl = null;
		Field[] f = null;
		String className = null;
		try
		{
			for(Object obj:objList)
			{
				JSONObject jsonObject =new JSONObject();
				className = obj.getClass().getCanonicalName();
				if(fieldHash.containsKey(className))
				{
					f=fieldHash.get(className);
				}
				else
				{
					cl = Class.forName(className);
					f = cl.getDeclaredFields();
					for(Field item:f)
					{
						item.setAccessible(true);
					}
					fieldHash.put(className, f);
				}		        
				String[] value = new String[f.length];
				for (int i = 0; i < f.length; i++) {
					value[i] = String.valueOf(f[i].get(obj));
					jsonObject.put(f[i].getName(), value[i]);
			}
				jsonArray.put( jsonObject);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return jsonArray;		
	}
	public static JSONObject generateJSON(Object obj) {
		JSONObject jsonObject = new JSONObject();
		Class<?> cl = null;
		Field[] f = null;
		String className = null;
		try 
		{

			className = obj.getClass().getCanonicalName();
			if(fieldHash.containsKey(className))
			{
				f=fieldHash.get(className);
			}
			else
			{
				cl = Class.forName(className);
				f = cl.getDeclaredFields();
				for(Field item:f)
				{
					item.setAccessible(true);
				}
				fieldHash.put(className, f);
			}		        

			String[] value = new String[f.length];
			for (int i = 0; i < f.length; i++) {
				value[i] = String.valueOf(f[i].get(obj));
			}

			for (int i = 0; i < f.length; i++) {
				jsonObject.put(f[i].getName(), value[i]);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

}
