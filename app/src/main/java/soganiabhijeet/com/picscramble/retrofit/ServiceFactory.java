package soganiabhijeet.com.picscramble.retrofit;

import retrofit.RestAdapter;

/**
 * Created by abhijeetsogani on 6/17/16.
 */
public class ServiceFactory {

    public static <T> T createRetrofitService(final Class<T> clazz, final String endPoint) {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endPoint)
                .build();
        T service = restAdapter.create(clazz);

        return service;
    }
}
