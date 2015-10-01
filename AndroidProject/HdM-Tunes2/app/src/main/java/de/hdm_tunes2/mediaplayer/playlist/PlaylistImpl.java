package de.hdm_tunes2.mediaplayer.playlist;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hdm_tunes2.mediaplayer.data.Track;

/**
 * Created by maximilianott on 27.04.15.
 */
public class PlaylistImpl implements Playlist {

    private Context context;
    private boolean repeat, shuffle;
    private Track currentTrack;
    private int currentIndex;
    private Set<CurrentTrackChangedListener> currentTrackChangedListeners = new HashSet<CurrentTrackChangedListener>();

    private static PlaylistImpl instance;

    //Constructor
    public PlaylistImpl (Context context) {
        this.context = context;
    }

    /**
     * If an instance exists, use it.
     * Else, create a new one. (Singleton Pattern)
     * @param context The context.
     * @return An instance of the playlist.
     */
    public synchronized static PlaylistImpl getInstance(Context context) {
        if(instance == null) {
            instance = new PlaylistImpl(context);
        }

        return instance;
    }


    // Creating the Playlist
    List<Track> tracks = Arrays.asList(new Track[]{
            new Track(
                    "Vorne ist oben und hinten ist unten.",
                    "Blumentopf",
                    "Großes Kino",
                    Uri.parse("android.resource://de.hdm_tunes2/raw/test1"),
                    Uri.parse("android.resource://de.hdm_tunes2/drawable/album01")),
            new Track(
                    "Outro",
                    "Topf mit Blumen",
                    "Kleines Kino",
                    Uri.parse("android.resource://de.hdm_tunes2/raw/test2"),
                    Uri.parse("android.resource://de.hdm_tunes2/drawable/album02")),
            new Track(
                    "Intro",
                    "Blumentöpfle",
                    "Kein Zufall",
                    Uri.parse("android.resource://de.hdm_tunes2/raw/test3"),
                    Uri.parse("android.resource://de.hdm_tunes2/drawable/album03"))
    });

    /**
     * Sets repeat functionality
     */
    @Override
    public void setRepeat(boolean repeat) { this.repeat = repeat; }

    /**
     * Sets shuffle functionality
     */
    @Override
    public void setShuffle(boolean shuffle) { this.shuffle = shuffle; }

    /**
     * returns the current value of the repeat variable
     *
     * @return the repeat variable (true or false)
     */
    @Override
    public boolean isRepeat() { return repeat; }

    /**
     * returns the current value of the shuffle variable
     *
     * @return the shuffle variable (true or false)
     */
    @Override
    public boolean isShuffle() { return shuffle; }

    /**
     * selects the next track in the playlist
     *
     * @return the "new" now selected currentTrack
     */
    @Override
    public Track selectNextTrack() {
        //if repeat is on and there is no next track -> start over from first track
        if(!repeat) {
            if (hasNextTrack()) {
                currentTrack = tracks.get(getCurrentIndex() + 1);
                synchronized (currentTrackChangedListeners) {
                    for (CurrentTrackChangedListener listener : currentTrackChangedListeners) {
                        listener.onCurrentTrackChanged(currentTrack, getCurrentIndex());
                    }
                }
            } else {
                return null;
            }
        }
        else {
                if (!hasNextTrack()) {
                    currentTrack = tracks.get(0);
                } else {
                    currentTrack = tracks.get(getCurrentIndex() + 1);
                }
            synchronized (currentTrackChangedListeners) {
                for (CurrentTrackChangedListener listener : currentTrackChangedListeners) {
                    listener.onCurrentTrackChanged(currentTrack, getCurrentIndex());
                }
            }
        }
        //if shuffle is on -> shuffle playlist -> then play "new" first track from list
        if(shuffle) {
            Collections.shuffle(tracks);
            currentTrack = tracks.get(0); //Erster Track in der Liste

            synchronized (currentTrackChangedListeners) {
                for (CurrentTrackChangedListener listener : currentTrackChangedListeners) {
                    listener.onCurrentTrackChanged(currentTrack, getCurrentIndex());
                }
            }
        }

        return currentTrack;
    }

    /**
     * selects the previous track in the playlist
     *
     * @return the "new" now selected currentTrack
     */
    @Override
    public Track selectPreviousTrack() {
        if(hasPreviousTrack()) {
            currentTrack = tracks.get(getCurrentIndex() - 1);
            synchronized (currentTrackChangedListeners) {
                for (CurrentTrackChangedListener listener : currentTrackChangedListeners) {
                    listener.onCurrentTrackChanged(currentTrack, getCurrentIndex());
                }
            }
        }
        // if no previous Track available, take first track from list
        else {
            currentTrack = tracks.get(0);
            synchronized (currentTrackChangedListeners) {
                for (CurrentTrackChangedListener listener : currentTrackChangedListeners) {
                    listener.onCurrentTrackChanged(currentTrack, getCurrentIndex());
                }
            }
        }
        return currentTrack;
    }

    /**
     * returns the currently selected track
     *
     * @return the currently selected track
     */
    @Override
    public Track getCurrentTrack() { return currentTrack; }

    /**
     * returns the index of the currently selected track
     *
     * @return the index of the currently selected track
     */
    @Override
    public int getCurrentIndex() {

        currentIndex = tracks.indexOf(currentTrack);
        return currentIndex;
    }

    /**
     * selects a track in the playlist at a specific position
     *
     * @param position index of the track to be selected
     *
     * @return the "new" currently selected track
     */
    @Override
    public synchronized Track selectTrackAtPosition(int position) {

        currentTrack = tracks.get(position);

            for (CurrentTrackChangedListener listener : currentTrackChangedListeners) {
                listener.onCurrentTrackChanged(currentTrack, getCurrentIndex());
            }
        return currentTrack;
    }

    /**
     * checks, if there is a at least one more track in the playlist after the currently selected one
     *
     * @return true if there is at least one more track, false otherwise
     */
    @Override
    public boolean hasNextTrack() {
        if(getCurrentIndex() == tracks.size() - 1) {
            return false;
        } else
            return true;
    }

    /**
     * checks, if there is a at least one more track in the playlist before the currently selected one
     *
     * @return true if there is at least one more track, false otherwise
     */
    @Override
    public boolean hasPreviousTrack() {
        if(getCurrentIndex() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * gets the list of all tracks in the playlist
     *
     * @return the list of tracks
     */
    @Override
    public List<Track> getTracks() {
        return tracks;
    }

    /**
     * Adds a {@link de.hdm_tunes2.mediaplayer.playlist.CurrentTrackChangedListener} to this de.hdm_tunes2.mediaplayer.playlist. This
     * listener will be informed when the currently selected track is changed
     *
     * @param listener
     *            the listener to add
     */
    @Override
    public void addCurrentTrackChangedListener(CurrentTrackChangedListener listener) {
        synchronized (currentTrackChangedListeners) {
            currentTrackChangedListeners.add(listener);
        }

    }

    /**
     * Removes a {@link de.hdm_tunes2.mediaplayer.playlist.CurrentTrackChangedListener} from this de.hdm_tunes2.mediaplayer.playlist.
     *
     * @param listener
     *            the listener to remove
     */
    @Override
    public void removeCurrentTrackChangedListener(CurrentTrackChangedListener listener) {
        synchronized (currentTrackChangedListeners) {
            currentTrackChangedListeners.remove(listener);
        }

    }
}
