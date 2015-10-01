/** 
 * Created on 25.02.2011 
 * 
 * © 2011 Daniel Thommes
 */
package de.hdm_tunes2.mediaplayer.player;

/**
 * Interface for all classes that want to observe the de.hdm_tunes2.mediaplayer.player's playback
 * position.
 * 
 * @author Daniel Thommes
 */
public interface PlaybackPositionUpdateListener {

	/**
	 * This method is called in regular intervals to update an instance of this
	 * interface of position updates. The call to this method will always take
	 * place in a special timer thread. Please be aware of multi-threading thow.
	 * 
	 * @param playbackPosition
	 *            the current playback position in milliseconds
	 * @param duration
	 *            the duration of the currently playing track.
	 */
	void onPlaybackPositionUpdate(int playbackPosition, int duration);

}
