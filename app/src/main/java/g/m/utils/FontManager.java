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

	private Typeface afl;
	private Typeface aflsolid;
	Typeface idroid;
	Typeface  android7;
	Typeface digital;
	Typeface district;
	Typeface gasalt;
	Typeface oxin;
	Typeface bold;
	Typeface light;
	Typeface regular;
	Typeface extrabold;
	Typeface zee;
    private Typeface fontSemiBold;




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
	public Typeface getFontAfl() {
		return this.afl;
	}

	public Typeface getFontAflsolid() {
		return this.aflsolid;
	}
	public Typeface getFontIdroid() {
		return this.idroid;
	}
	public Typeface getFontAndroid7() {
		return this.android7;
	}
	public Typeface getFontDigital() {
		return this.digital;
	}
	public Typeface getFontDistrict() {
		return this.district;
	}
	public Typeface getFontGasalt() {
		return this.gasalt;
	}
	public Typeface getFontOxin() {
		return this.oxin;
	}
	public Typeface getFontBold() {
		return this.bold;
	}
    public Typeface getFontLight() {
        return this.light;
    }
    public Typeface getFontRegular() {
        return this.regular;
    }
    public Typeface getFontExtraBold() {
        return this.extrabold;
    }
    public Typeface getFontZee() {
        return this.zee;
    }
    public Typeface getFontSemiBold() {
        return this.fontSemiBold;
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

		this.afl = Typeface.createFromAsset(context.getAssets(), "fonts/afl.ttf");
		this.aflsolid = Typeface.createFromAsset(context.getAssets(), "fonts/aflsolid.ttf");
		this.idroid = Typeface.createFromAsset(context.getAssets(), "fonts/idroid.otf");
		this.android7 = Typeface.createFromAsset(context.getAssets(), "fonts/android7.ttf");
		this.digital = Typeface.createFromAsset(context.getAssets(), "fonts/digital.otf");
		this.district = Typeface.createFromAsset(context.getAssets(), "fonts/district.ttf");
		this.gasalt = Typeface.createFromAsset(context.getAssets(), "fonts/gasalt.ttf");
		this.oxin = Typeface.createFromAsset(context.getAssets(), "fonts/oxin.ttf");
		this.bold= Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Bold.ttf");
		this.light= Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Light.ttf");
		this.regular= Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Regular.ttf");
		this.extrabold= Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-ExtraBold.ttf");
		this.zee= Typeface.createFromAsset(context.getAssets(), "fonts/zee.ttf");
        this.fontSemiBold = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-SemiBold.ttf");
	}


}