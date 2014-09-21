package com.example.themeresource;

import android.app.Application;
import android.content.Context;

public class MainApp extends Application {

	private static final String FIELD_RESOURCE_NAME = "mResources"; // ContextImpl中的Resource变量名
	private static final String FIELD_PKGINFO_NAME = "mPackageInfo"; // ContextImpl中的PackageInfo变量名

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		changeIntoOwnResource(base);
	}

	public static void changeIntoOwnResource(Context base) {
			// change for context.getResources()
			OwnThemeResources ownRes = OwnThemeResources.getInstance(base
					.getResources());
			ReflectionUtils.modifyFieldValue(base.getClass(),
					FIELD_RESOURCE_NAME, base, ownRes);

			ReflectionUtils.modifyFieldValue(base.getClass().getSuperclass(),
					FIELD_RESOURCE_NAME, base, ownRes);

			// change for package.getResources()
			Object pkgInfo = ReflectionUtils.getPrivateField(base,
					FIELD_PKGINFO_NAME, base.getClass());
			ReflectionUtils.modifyFieldValue(pkgInfo.getClass(),
					FIELD_RESOURCE_NAME, pkgInfo, ownRes);
	}

}
