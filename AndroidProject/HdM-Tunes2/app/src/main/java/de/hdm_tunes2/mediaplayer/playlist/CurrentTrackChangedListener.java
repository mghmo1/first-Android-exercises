/** 
 * Created on 02.03.2011 
 * 
 * © 2011 Daniel Thommes
 */
package de.hdm_tunes2.mediaplayer.playlist;

import de.hdm_tunes2.mediaplayer.data.Track;

/**
 * Listener that is called when the de.hdm_tunes2.mediaplayer.playlist's current track changes.
 * 
 * @author Daniel Thommes
 */
public interface CurrentTrackChangedListener {

	/**
	 * This method is called after the de.hdm_tunes2.mediaplayer.playlist's current track changed.
	 * 
	 * @param newCurrentTrack
	 *            the new track
	 * @param position
	 *            the position of the new track in the de.hdm_tunes2.mediaplayer.playlist
	 */
	void onCurrentTrackChanged(Track newCurrentTrack, int position);

}
