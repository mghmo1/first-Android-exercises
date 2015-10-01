/**
 * Created on 23.03.2011
 *
 * © 2011 Daniel Thommes
 */
package de.hdm_tunes2.mediaplayer.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import de.hdm_tunes2.mediaplayer.data.Track;

/**
 * This class is the music de.hdm_tunes2.mediaplayer.player component and implements the Player interface
 *
 * @author maximilianott
 */
public class PlayerImpl implements Player {

    private static final String TAG = "HdM-Tunes";

    private Context context;
    private MediaPlayer mediaPlayer;

    /**
     * A variable for the current track.
     */
    private Track currentTrack;

    /**
     * A variable for the current state of the mediaplayer.
     */
    private PlayerState currentState;
    /**
     * The Uri of the {@link Track}.
     */
    private Uri myUri;

    private Timer timer;
    private Updater updater;

    private Set<PlaybackCompletionListener> playbackCompletionListeners = new HashSet<PlaybackCompletionListener>();
    private Set<PlaybackPositionUpdateListener> positionUpdateListeners = new HashSet<PlaybackPositionUpdateListener>();
    private Set<TrackLoadedListener> trackLoadedListeners = new HashSet<TrackLoadedListener>();

    private static PlayerImpl instance;

    PlayerImpl(Context context){  // Konstruktor

        this.context = context;
        timer = new Timer();
        mediaPlayer = new MediaPlayer();
        currentState = PlayerState.empty;
    }

    /**
     * If an instance exists, use it.
     * Else, create a new one. (Singleton Pattern)
     * @param context The context.
     * @return An instance of the player.
     */
    public synchronized static PlayerImpl getInstance(Context context) {
        if(instance == null) {
            instance = new PlayerImpl(context);
        }

        return instance;
    }

    /**
     * This class updates the {@link PlaybackPositionUpdateListener}
     */
    public class Updater extends TimerTask {

        /**
         *
         */
        @Override
        public void run() {

            if(mediaPlayer.isPlaying()){
                synchronized (positionUpdateListeners) {
                    for (PlaybackPositionUpdateListener listener : positionUpdateListeners) {
                        listener.onPlaybackPositionUpdate(mediaPlayer.getCurrentPosition(), mediaPlayer.getDuration());
                    }
                }
            }
        }
    }

    /**
     * Loads the given {@link Track}.
     *
     * @param track The {@link Track} to load.
     */
	public synchronized void load(Track track) {

        currentTrack = track;
        myUri = currentTrack.getUri();
        //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {

            if (getState() == PlayerState.playing) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.setDataSource(context, myUri);
                mediaPlayer.prepare();
            } else {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(context, myUri);
                mediaPlayer.prepare();
            }

            for (TrackLoadedListener listener : trackLoadedListeners) {
                Log.d(TAG, "Track loaded");
                listener.onTrackLoaded(currentTrack);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch(currentState) {
            case empty: {
                currentState = PlayerState.empty;
                break;
            }
            case playing: {
                currentState = PlayerState.playing;
                break;
            }
            case paused: {
                currentState = PlayerState.paused;
                break;
            }
        }
    }

    /**
     * Starts the playback of the currently loaded track. If no track is loaded
     * (state is <code>empty</code>), a call to this method will not have an
     * effect. If the state of the de.hdm_tunes2.mediaplayer.player is <code>playbackComplete</code>, a
     * call to this method will result in replaying the currently loaded track
     * from the beginning.
     */
	public synchronized void play() {

        updater = new Updater();
        timer.schedule(updater, 500, 1000 / 18);

        mediaPlayer.start();
        currentState = PlayerState.playing;
    }

    /**
     * Pauses the playback of the currently playing track. A call to this method
     * only has an effect, if the de.hdm_tunes2.mediaplayer.player is in state <code>playing</code>.
     */
	public synchronized void pause() {

        updater.cancel();
        //timer.cancel();

        if(getState() == PlayerState.playing) {
            mediaPlayer.pause();
            currentState = PlayerState.paused;
        }
	}

    /**
     * Returns the track that is currently playing
     *
     * @return the track that is currently playing
     */
	public Track getCurrentTrack() {
		return currentTrack;
	}

    /**
     * Returns the playback position of the Player in milliseconds.
     *
     * @return the playback position of the Player in milliseconds
     */
	public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
	}

    /**
     * Returns the de.hdm_tunes2.mediaplayer.player's current state.
     *
     * @return the de.hdm_tunes2.mediaplayer.player's current state.
     */
	public PlayerState getState() {
		return currentState;
	}

    /**
     * Returns the duration of the currently loaded track in milliseconds.
     *
     * @return the duration of the currently loaded track in milliseconds
     */
	public synchronized int getDuration() {
		return mediaPlayer.getDuration();
	}


    /**
     * Adds a {@link PlaybackPositionUpdateListener} to this de.hdm_tunes2.mediaplayer.player. This
     * listener will regularly be informed about position updates while the
     * de.hdm_tunes2.mediaplayer.player is playing.
     *
     * @param listener
     *            the listener to add
     */
	public void addPlaybackPositionUpdateListener (
			PlaybackPositionUpdateListener listener) {

        synchronized (positionUpdateListeners) {
           positionUpdateListeners.add(listener);
        }
	}


    /**
     * Removes a {@link PlaybackPositionUpdateListener} from this de.hdm_tunes2.mediaplayer.player.
     *
     * @param listener
     *            the listener to remove
     */
	public void removePlaybackPositionUpdateListener(
			PlaybackPositionUpdateListener listener) {

        synchronized (positionUpdateListeners) {
            positionUpdateListeners.remove(listener);
        }
	}


    /**
     * Adds a {@link PlaybackCompletionListener} to this de.hdm_tunes2.mediaplayer.player.
     *
     * @param listener
     *            the listener to add
     */
	public void addPlaybackCompleteListener(PlaybackCompletionListener listener) {

        synchronized (playbackCompletionListeners) {
            playbackCompletionListeners.add(listener);
        }
	}


    /**
     * Removes a {@link PlaybackCompletionListener} from this de.hdm_tunes2.mediaplayer.player.
     *
     * @param listener
     *            the listener to remove
     */
	public void removePlaybackCompleteListener(
			PlaybackCompletionListener listener) {

        synchronized (playbackCompletionListeners) {
            playbackCompletionListeners.remove(listener);
        }
	}


    /**
     * Adds a {@link TrackLoadedListener} to this de.hdm_tunes2.mediaplayer.player.
     *
     * @param listener
     *            the listener to add
     */
	public void addTrackLoadedListener(final TrackLoadedListener listener) {

        synchronized (trackLoadedListeners) {
            trackLoadedListeners.add(listener);
        }
	}


    /**
     * Removes a {@link TrackLoadedListener} from this de.hdm_tunes2.mediaplayer.player.
     *
     * @param listener
     *            the listener to remove
     */
	public void removeTrackLoadedListener(TrackLoadedListener listener) {

        synchronized (trackLoadedListeners) {
            trackLoadedListeners.remove(listener);
        }
	}
}
