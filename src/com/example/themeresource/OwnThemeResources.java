package com.example.themeresource;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class OwnThemeResources extends Resources{
    private static final String FIELD_COMPATIBILITY_NAME = "mCompatibilityInfo";

    private static OwnThemeResources sInstance;
	
    private OwnThemeResources(Resources res) {
        super(res.getAssets(), res.getDisplayMetrics(), res.getConfiguration());
        
        Object compatibilityInfo = ReflectionUtils.getPrivateField(res, FIELD_COMPATIBILITY_NAME, Resources.class);
        if (compatibilityInfo != null) {
            boolean ret = ReflectionUtils.modifyFieldValue(Resources.class, FIELD_COMPATIBILITY_NAME, this, compatibilityInfo);
            if (ret) {
                updateConfiguration(this.getConfiguration(), this.getDisplayMetrics());
            }
        }
    }
    
    public static OwnThemeResources getInstance(Resources res) {
        if (sInstance == null) {
            sInstance = new OwnThemeResources(res);
        }
        return sInstance;
    }
    
    @Override
    public Drawable getDrawable(int id) throws NotFoundException {
    	Log.i("Dozen", "OwnResources getDrawable :" + getResourceEntryName(id));
    	return super.getDrawable(id);
    }
    
    @Override
    public String getString(int id) throws NotFoundException {
    	Log.i("Dozen", "OwnResources getString :" + getResourceEntryName(id));
    	return super.getString(id);
    }
    
    @Override
    public int getColor(int id) throws NotFoundException {
    	Log.i("Dozen", "OwnResources getColor :" + getResourceEntryName(id));
    	return super.getColor(id);
    }


}
