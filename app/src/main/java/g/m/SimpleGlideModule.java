package g.m;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by kush.agrawal on 12/6/2016.
 */
public class SimpleGlideModule implements GlideModule{
    @Override public void applyOptions(Context context, GlideBuilder builder) {
        // todo
    }

    @Override public void registerComponents(Context context, Glide glide) {
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().connectTimeout(15, TimeUnit.SECONDS);
        client.newBuilder().readTimeout(15, TimeUnit.SECONDS);
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(client);
        glide.register(GlideUrl.class, InputStream.class, factory);
    }
}
