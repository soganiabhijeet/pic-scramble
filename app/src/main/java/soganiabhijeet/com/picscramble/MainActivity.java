package soganiabhijeet.com.picscramble;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import soganiabhijeet.com.picscramble.adapter.GridAdapter;
import soganiabhijeet.com.picscramble.model.FlickrModel;
import soganiabhijeet.com.picscramble.retrofit.FlickrService;
import soganiabhijeet.com.picscramble.model.Items;
import soganiabhijeet.com.picscramble.retrofit.ServiceFactory;
import soganiabhijeet.com.picscramble.utils.Constants;


public class MainActivity extends AppCompatActivity implements GridAdapter.OnItemClickListener {

    private GridAdapter imageAdapter;
    private ArrayList<Items> imageList;
    private List<Integer> showedImagesPosition;
    private ImageView singleImage;
    private int identifiedImageCount;
    private boolean isImageFlipped;
    private int currentImagePosition;
    private ProgressBar progressBar;
    private TextView selectionText;
    private TextView timerText;
    private Subscription animationSubscription;
    Subscription networkCallSubscription;
    private View mainLayout;
    private FlickrService service;
    int count = Constants.DELAY_SECONDS;
    private View timerLayout;
    private CompositeSubscription rxSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rxSubscriber = new CompositeSubscription();
        initViews();
        service = ServiceFactory.createRetrofitService(FlickrService.class, Constants.SERVICE_ENDPOINT);
        makeNetworkCall();

    }

    private void initViews() {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        singleImage = (ImageView) findViewById(R.id.single_image);
        selectionText = (TextView) findViewById(R.id.selection_text);
        timerText = (TextView) findViewById(R.id.timer_text);
        timerLayout = findViewById(R.id.timer_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.images_grid);
        progressBar.setVisibility(View.VISIBLE);
        imageList = new ArrayList<>();
        showedImagesPosition = new ArrayList<>();
        imageAdapter = new GridAdapter(getApplication(), this);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 3));
        mainLayout = findViewById(R.id.main_layout);
        recyclerView.setAdapter(imageAdapter);


    }


    private void makeNetworkCall() {
        networkCallSubscription = service.getImages()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FlickrModel>() {
                    @Override
                    public final void onCompleted() {
                        // do nothing
                    }

                    @Override
                    public final void onError(Throwable e) {
                    }

                    @Override
                    public final void onNext(FlickrModel response) {
                        for (int i = Constants.COUNT_ZERO; i < Constants.COUNT_NINE; i++) {
                            response.getItems()[i].setIsPositionIdentified(false);
                            imageList.add(response.getItems()[i]);
                        }
                        imageAdapter.addItems(imageList);
                        showSnackbar(getString(R.string.flip_text), Snackbar.LENGTH_LONG);
                        createTimer();
                        hideProgressBar();
                    }
                });
        rxSubscriber.add(networkCallSubscription);
    }

    private void hideProgressBar() {
        if (progressBar != null && progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void createTimer() {
        animationSubscription = Observable.just(1).delay(1, TimeUnit.SECONDS).repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer Void) {
                        setTimerText();
                    }
                });
        rxSubscriber.add(animationSubscription);

    }

    private void setTimerText() {
        if (count-- > 0) {
            timerText.setText(String.valueOf(count));
        } else {
            animationSubscription.unsubscribe();
            timerLayout.setVisibility(View.GONE);
            imageAdapter.updateGameState(Constants.GridStates.USER_PLAYING);
            showSnackbar(getString(R.string.game_start), Snackbar.LENGTH_SHORT);
            updateImageOnScreen();
        }

    }




    private void checkImagePosition(int clickedPosition) {
        if (clickedPosition == currentImagePosition) {
            showSnackbar(getString(R.string.contrats_text), Snackbar.LENGTH_SHORT);
            identifiedImageCount++;
            imageList.get(clickedPosition).setIsPositionIdentified(true);
            imageAdapter.itemIdentified(clickedPosition);
            updateImageOnScreen();
        } else {
            showSnackbar(getString(R.string.incorrect_position_text), Snackbar.LENGTH_SHORT);
        }
    }

    private void updateImageOnScreen() {
        if (identifiedImageCount == Constants.COUNT_NINE) {
            showCompletionView();
        } else {
            showNextImageView();
        }
    }

    private void showCompletionView() {
        singleImage.setVisibility(View.GONE);
        selectionText.setText(getString(R.string.completion_text));
        imageAdapter.updateGameState(Constants.GridStates.USER_WON);
    }

    private void showNextImageView() {
        int position = generateRandomPosition();
        Glide.with(this).load(imageList.get(position).media.getM())
                .into(singleImage);
        showedImagesPosition.add(position);
        currentImagePosition = position;
        singleImage.setVisibility(View.VISIBLE);
        selectionText.setVisibility(View.VISIBLE);
    }

    public int generateRandomPosition() {
        boolean validPosition = false;
        Random random = new Random();
        int randomNumber = -1;
        while (!validPosition) {
            randomNumber = random.nextInt((Constants.COUNT_EIGHT - Constants.COUNT_ZERO) + Constants.COUNT_ONE) + Constants.COUNT_ZERO;
            if (randomNumber > -1 && !showedImagesPosition.contains(randomNumber)) {
                validPosition = true;
            }
        }
        return randomNumber;
    }

    private void showSnackbar(String textString, int length) {
        Snackbar snackbar = Snackbar
                .make(mainLayout, textString, length);

        snackbar.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rxSubscriber.unsubscribe();
    }

    @Override
    public void onItemClick(int position) {

        checkImagePosition(position);

    }
}
