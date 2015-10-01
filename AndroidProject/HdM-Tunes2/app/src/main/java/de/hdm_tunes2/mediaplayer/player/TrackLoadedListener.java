/**
 * Created on 26.02.2011
 * 
 * © 2011 Daniel Thommes
 */
package de.hdm_tunes2.mediaplayer.player;

import de.hdm_tunes2.mediaplayer.data.Track;


/**
 * Listener that is called after the de.hdm_tunes2.mediaplayer.player has loaded a new track.
 * 
 * @author Daniel Thommes
 */
public interface TrackLoadedListener {

	/**
	 * This method is called after the de.hdm_tunes2.mediaplayer.player has loaded a new track.
	 * 
	 * @param track
	 *            the new track
	 */
	void onTrackLoaded(Track track);

}
