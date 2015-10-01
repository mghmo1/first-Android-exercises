/** 
 * Created on 25.02.2011 
 * 
 * © 2011 Daniel Thommes
 */
package de.hdm_tunes2.mediaplayer.playlist;

import java.util.List;

import de.hdm_tunes2.mediaplayer.data.Track;

/**
 * Interface for the Playlist component
 * 
 * @author Daniel Thommes
 */
public interface Playlist {

	/**
	 * Toggles whether the de.hdm_tunes2.mediaplayer.playlist is in repeat all mode. In this case the
	 * getNextTrack and hasNextTrack methods behave accordingly.
	 * 
	 * @param repeat
	 */
	void setRepeat(boolean repeat);

	/**
	 * Toggles whether the de.hdm_tunes2.mediaplayer.playlist is in shuffle mode.
	 * 
	 * @param shuffle
	 *            flag indicating whether the de.hdm_tunes2.mediaplayer.playlist is in shuffle mode
	 */
	void setShuffle(boolean shuffle);

	/**
	 * Indicates whether the de.hdm_tunes2.mediaplayer.playlist is in repeat all mode
	 * 
	 * @return <code>true</code>, if the de.hdm_tunes2.mediaplayer.playlist is in repeat all mode,
	 *         <code>false</code> otherwise
	 */
	boolean isRepeat();

	/**
	 * Indicates whether the de.hdm_tunes2.mediaplayer.playlist is in shuffle mode
	 * 
	 * @return <code>true</code>, if the de.hdm_tunes2.mediaplayer.playlist is in shuffle mode,
	 *         <code>false</code> otherwise
	 */
	boolean isShuffle();

	/**
	 * Returns the next track in the de.hdm_tunes2.mediaplayer.playlist or null if there is no next track.
	 * If the de.hdm_tunes2.mediaplayer.playlist is in repeat mode, calling this method while the last
	 * track is selected will skip to the first track in the list. Calling this
	 * method while in shuffle mode returns a random Track from the de.hdm_tunes2.mediaplayer.playlist.
	 * 
	 * @return the next Track in the de.hdm_tunes2.mediaplayer.playlist according to the rules explained
	 *         above
	 */
	Track selectNextTrack();

	/**
	 * Returns the previously selected Track.
	 * 
	 * @return the previously selected Track
	 */
	Track selectPreviousTrack();

	/**
	 * Returns the currently selected Track or null, if there is no track
	 * selected.
	 * 
	 * @return the currently selected Track
	 */
	Track getCurrentTrack();

	/**
	 * Returns the index (position) of the currently selected Track within the
	 * de.hdm_tunes2.mediaplayer.playlist.
	 * 
	 * @return the index (position) of the currently selected Track
	 */
	int getCurrentIndex();

	/**
	 * Returns the Track at a specified position within the de.hdm_tunes2.mediaplayer.playlist. A call to
	 * this method will also set the selected Track as the currentTrack.
	 * 
	 * @param position
	 *            position of the Track to be returned
	 * @return the Track at the position or null if there is no track.
	 */
	Track selectTrackAtPosition(int position);

	/**
	 * Indicates whether a call to getNextTrack will return something when you
	 * call it.
	 * 
	 * @return <code>true</code> if there is a next track, <code>false</code>
	 *         otherwise
	 */
	boolean hasNextTrack();

	/**
	 * Indicates whether a call to getPreviousTrack will return something when
	 * you call it.
	 * 
	 * @return <code>true</code> if there is a previous track,
	 *         <code>false</code> otherwise
	 */
	boolean hasPreviousTrack();

	/**
	 * Returns a List of all tracks in this de.hdm_tunes2.mediaplayer.playlist.
	 * 
	 * @return a List of all tracks in this de.hdm_tunes2.mediaplayer.playlist.
	 */
	List<Track> getTracks();

	/**
	 * Adds a {@link CurrentTrackChangedListener} to this instance that will be
	 * notified, if another component calls a method that results in the current
	 * track being changed.
	 * 
	 * @param listener
	 *            Listener that will be notified about track changes.
	 */
	void addCurrentTrackChangedListener(CurrentTrackChangedListener listener);

	/**
	 * Removes a {@link CurrentTrackChangedListener} from this instance.
	 * 
	 * @param listener
	 *            the listener to remove
	 */
	void removeCurrentTrackChangedListener(CurrentTrackChangedListener listener);

}
