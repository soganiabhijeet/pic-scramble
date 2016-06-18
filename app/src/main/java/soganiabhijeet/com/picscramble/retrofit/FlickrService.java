package soganiabhijeet.com.picscramble.retrofit;

import retrofit.http.GET;
import rx.Observable;
import soganiabhijeet.com.picscramble.model.FlickrModel;

/**
 * Created by abhijeetsogani on 6/17/16.
 */
public interface FlickrService {


    @GET("/")
    Observable<FlickrModel> getImages();
}