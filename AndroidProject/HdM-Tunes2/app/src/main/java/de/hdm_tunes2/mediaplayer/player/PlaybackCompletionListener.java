/** 
 * Created on 25.02.2011 
 * 
 * © 2011 Daniel Thommes
 */
package de.hdm_tunes2.mediaplayer.player;

/**
 * Listener that is called, when the de.hdm_tunes2.mediaplayer.player has completed the playback of the
 * current track.
 * 
 * @author Daniel Thommes
 */
public interface PlaybackCompletionListener {

	/**
	 * This method is called, when the de.hdm_tunes2.mediaplayer.player has completed the playback of the
	 * current track.
	 */
	void onPlaybackComplete();

}
