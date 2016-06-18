package soganiabhijeet.com.picscramble.utils;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.bumptech.glide.module.GlideModule;

import java.io.File;
import java.io.InputStream;

/**
 * Created by abhijeetsogani on 6/18/16.
 */
public class GlideConfiguration implements GlideModule {


    @Override
    public void applyOptions(Context context, GlideBuilder glideBuilder) {
        final String cacheDirectoryName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Constants.PIC_SCRAMBLE + "/" + Constants.CACHE_DIR;
        final int diskCacheSize = 10 * 1024 * 1024; //10 MB
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();

        DiskCache.Factory diskCacheFactory = new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                DiskCache diskCache = DiskLruCacheWrapper.get(new File(cacheDirectoryName), diskCacheSize);
                return diskCache;
            }
        };
        glideBuilder.setDiskCache(diskCacheFactory);
        //memory cache is calculated based on device. Just setting the same again
        glideBuilder.setMemoryCache(new LruResourceCache(defaultMemoryCacheSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(String.class, InputStream.class, new HeaderedLoader.Factory());
    }

    private static class HeaderedLoader extends BaseGlideUrlLoader<String> {

        public HeaderedLoader(Context context) {
            super(context);
        }

        @Override
        protected String getUrl(String model, int width, int height) {
            return model;
        }

        @Override
        protected Headers getHeaders(String model, int width, int height) {
            //Add headers in future after Builder()
            return new LazyHeaders.Builder().build();
        }

        public static class Factory implements ModelLoaderFactory<String, InputStream> {
            @Override
            public StreamModelLoader<String> build(Context context, GenericLoaderFactory factories) {
                return new HeaderedLoader(context);
            }

            @Override
            public void teardown() {
             /* nothing to free */
            }
        }
    }

}