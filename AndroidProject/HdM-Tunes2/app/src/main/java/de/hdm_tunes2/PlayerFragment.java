package de.hdm_tunes2;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import de.hdm_tunes2.mediaplayer.data.Track;
import de.hdm_tunes2.mediaplayer.player.PlaybackCompletionListener;
import de.hdm_tunes2.mediaplayer.player.PlaybackPositionUpdateListener;
import de.hdm_tunes2.mediaplayer.player.Player;
import de.hdm_tunes2.mediaplayer.player.PlayerImpl;
import de.hdm_tunes2.mediaplayer.player.TrackLoadedListener;
import de.hdm_tunes2.mediaplayer.playlist.PlaylistImpl;

/**
 * This class provides the interaction between the layout and the business logic of the player component.
 */
public class PlayerFragment extends Fragment
       implements OnClickListener, PlaybackPositionUpdateListener, PlaybackCompletionListener, TrackLoadedListener {

    /**
     * This tag is used for logging.
     */
    private static final String TAG = "HdM-Tunes";

    PlayerImpl myPlayer;
    PlaylistImpl myPlaylist;

    Context context;

    private TextView songTitle;
    private TextView artist;
    private TextView progressText;
    private TextView durationResuming;
    private ProgressBar songProgress;
    private ImageView albumCover;
    private ImageButton playButton;
    private ImageButton repeatButton;
    private ImageButton shuffleButton;
    private ImageButton skipNextButton;
    private ImageButton skipPreviousButton;


    /**
     * Getting references to the layout elements and setting the click listeners.
     * The mapping between layout and code is done by the Android R class.
     * Instead of using the onCreate-method we use onCreateView, because of working with fragments.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    private String time = "";
    private String remaining = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_player,container,false);
        context = view.getContext();

        songTitle = (TextView) view.findViewById(R.id.songTitle);
        artist = (TextView) view.findViewById(R.id.artist);
        songProgress = (ProgressBar) view.findViewById(R.id.progressBar);
        albumCover = (ImageView) view.findViewById(R.id.albumCover);

        progressText = (TextView) view.findViewById(R.id.progress);
        durationResuming = (TextView) view.findViewById(R.id.duration);


        playButton = (ImageButton) view.findViewById(R.id.playButton);
        repeatButton = (ImageButton) view.findViewById(R.id.repeatButton);
        shuffleButton = (ImageButton) view.findViewById(R.id.shuffleButton);
        skipNextButton = (ImageButton) view.findViewById(R.id.skipNextButton);
        skipPreviousButton = (ImageButton) view.findViewById(R.id.skipPreviousButton);

        playButton.setOnClickListener(this);
        repeatButton.setOnClickListener(this);
        shuffleButton.setOnClickListener(this);
        skipNextButton.setOnClickListener(this);
        skipPreviousButton.setOnClickListener(this);

        return view;
    }

    /**
     * This function handles what happens if something in the player view is clicked.
     * @param v The view.
     */
    @Override
    public void onClick(View v) {
        //clicks auf verschiedene Bedienelemente handeln
        switch (v.getId()) {
            case R.id.playButton:
                if(myPlayer.getState() == Player.PlayerState.playing){
                    myPlayer.pause();
                    playButton.setImageResource(R.drawable.play);

                } else if (myPlayer.getState() == Player.PlayerState.empty){
                    playButton.setImageResource(R.drawable.play);

                } else if (myPlayer.getState() == Player.PlayerState.paused) {
                    myPlayer.play();
                    playButton.setImageResource(R.drawable.pause);
                }
            break;

            case R.id.repeatButton:
                if(myPlaylist.isRepeat()) {
                    myPlaylist.setRepeat(false);
                    repeatButton.setColorFilter(Color.argb(255, 130, 130, 130));
                } else {
                    repeatButton.setColorFilter(Color.argb(255,0,0,0));
                    myPlaylist.setRepeat(true);
                }
            break;

            case R.id.shuffleButton:
                if(myPlaylist.isShuffle()) {
                    myPlaylist.setShuffle(false);
                    shuffleButton.setColorFilter(Color.argb(255, 130, 130, 130));
                } else {
                    myPlaylist.setShuffle(true);
                    shuffleButton.setColorFilter(Color.argb(255, 0, 0, 0));
                }
            break;

            case R.id.skipNextButton:
                myPlaylist.selectNextTrack();
                myPlayer.load(myPlaylist.getCurrentTrack());
                myPlayer.play();
            break;

            case R.id.skipPreviousButton:
                myPlaylist.selectPreviousTrack();
                myPlayer.load(myPlaylist.getCurrentTrack());
                myPlayer.play();
            break;
        }
    }

    /**
     * If the playback is complete:
     *  - if playlist has a next track, load next track in playlist
     */
    @Override
    public void onPlaybackComplete() {
        if(myPlaylist.hasNextTrack()) {
            myPlaylist.selectNextTrack();
            myPlayer.load(myPlaylist.getCurrentTrack());
            myPlayer.play();
        }
    }

    /**
     * If the playback position is updated:
     * - the actual position is provided to the progressbar
     * - when playback is started or resumed, the image resource for the playbutton is changed to pause
     * @param playbackPosition
     *            the current playback position in milliseconds
     * @param duration
     *            the duration of the current track
     */
    @Override
    public void onPlaybackPositionUpdate(final int playbackPosition, final int duration) {

        /**
         * Using runOnUiThread because updating the UI from the backend.
         */
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                playButton.setImageResource(R.drawable.pause);
                songProgress.setMax(duration);
                songProgress.setProgress(playbackPosition);

                time = String.format("%02d:%02d",playbackPosition / 1000 / 60, playbackPosition / 1000);
                remaining = String.format("%02d:%02d", (duration - playbackPosition) / 1000 / 60, (duration - playbackPosition) / 1000);

                progressText.setText(time);

                durationResuming.setText("- " + remaining);
            }
            });
    }

    /**
     * At first the title, the artist and the album cover are set.
     * Afterwards the duration of the track is assigned to the progressbar.
     * @param track
     *          the current track
     */
    @Override
    public void onTrackLoaded(final Track track) {

        /**
         * Using runOnUiThread because updating the UI from the backend.
         */
        getActivity().runOnUiThread(new Runnable() {
             @Override
            public void run() {


                 songTitle.setText(track.getTitle());
                 artist.setText(track.getArtist());
                 albumCover.setImageURI(track.getAlbumArtUri());

                 songProgress.setMax(track.getDuration());
                 songProgress.setProgress(0);
            }
        });
    }

    /**
     * Instanciating {@link de.hdm_tunes2.mediaplayer.player.Player} and {@link de.hdm_tunes2.mediaplayer.playlist.PlaylistImpl}.
     * Register the listeners.
     */
    @Override
    public void onStart() {
        super.onStart();

        myPlayer = PlayerImpl.getInstance(context);
        myPlaylist = PlaylistImpl.getInstance(context);

        myPlayer.addPlaybackPositionUpdateListener(this);
        myPlayer.addPlaybackCompleteListener(this);
        myPlayer.addTrackLoadedListener(this);
    }

    /**
     * Remove the instances of {@link de.hdm_tunes2.mediaplayer.player.Player} and {@link de.hdm_tunes2.mediaplayer.playlist.PlaylistImpl}.
     * Stops the activity.
     */
    @Override
    public void onStop() {
        super.onStop();

        myPlayer.removePlaybackPositionUpdateListener(this);
        myPlayer.removeTrackLoadedListener(this);
        myPlayer.removePlaybackCompleteListener(this);

        myPlayer = null;
        myPlaylist = null;
    }

    /**
     * Pausing the activity.
     */
    @Override
    public void onPause() {
        super.onPause();
    }
}