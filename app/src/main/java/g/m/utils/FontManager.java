package g.m.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by 5dr on 05/12/16.
 */

public class FontManager {


	private static FontManager instance;
	private Typeface chargen;
	private Typeface easports;
	private Typeface minecraft;

	private FontManager() {
	}

	public Typeface getFontChargen() {
		return this.chargen;
	}

	public Typeface getFontEasports() {
		return this.easports;
	}

	public Typeface getFontMinecraft() {
		return this.minecraft;
	}


	public static FontManager get() {
		if (instance == null) {
			instance = new FontManager();
		}
		return instance;
	}

	public void initFonts(Context context) {
		this.chargen = Typeface.createFromAsset(context.getAssets(), "fonts/chargen.ttf");
		this.easports = Typeface.createFromAsset(context.getAssets(), "fonts/easports.ttf");
		this.minecraft = Typeface.createFromAsset(context.getAssets(), "fonts/minecraft.ttf");
	}

	/*

    Typeface easports = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/easports.ttf");
	Typeface idroid = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/idroid.otf");
	Typeface  android7 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/android7.ttf");
	Typeface digital = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/digital.otf");
	Typeface district = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/district.ttf");
	Typeface gasalt = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/gasalt.ttf");
	Typeface oxin = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/oxin.ttf");
	Typeface spac = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/spac.ttf");
	Typeface zee = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/zee.ttf");*/
}