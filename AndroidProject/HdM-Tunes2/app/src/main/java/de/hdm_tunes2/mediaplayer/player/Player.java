/** 
 * Created on 25.02.2011 
 * 
 * © 2011 Daniel Thommes
 */
package de.hdm_tunes2.mediaplayer.player;

import de.hdm_tunes2.mediaplayer.data.Track;

/**
 * Interface for the music de.hdm_tunes2.mediaplayer.player component
 * 
 * @author Daniel Thommes
 */
public interface Player {

	/**
	 * States the de.hdm_tunes2.mediaplayer.player can be in
	 * 
	 * @author Daniel Thommes
	 */
	enum PlayerState {
		empty, paused, playing, playbackComplete
	}

	/**
	 * Loads the given {@link Track}.
	 * 
	 * @param track
	 *            The {@link Track} to load.
	 */
	void load(Track track);

	/**
	 * Starts the playback of the currently loaded track. If no track is loaded
	 * (state is <code>empty</code>), a call to this method will not have an
	 * effect. If the state of the de.hdm_tunes2.mediaplayer.player is <code>playbackComplete</code>, a
	 * call to this method will result in replaying the currently loaded track
	 * from the beginning.
	 */
	void play();

	/**
	 * Pauses the playback of the currently playing track. A call to this method
	 * only has an effect, if the de.hdm_tunes2.mediaplayer.player is in state <code>playing</code>.
	 */
	void pause();

	/**
	 * Returns the track that is currently playing
	 * 
	 * @return the track that is currently playing
	 */
	Track getCurrentTrack();

	/**
	 * Returns the playback position of the Player in milliseconds.
	 * 
	 * @return the playback position of the Player in milliseconds
	 */
	int getCurrentPosition();

	/**
	 * Returns the de.hdm_tunes2.mediaplayer.player's current state.
	 * 
	 * @return the de.hdm_tunes2.mediaplayer.player's current state.
	 */
	PlayerState getState();

	/**
	 * Returns the duration of the currently loaded track in milliseconds.
	 * 
	 * @return the duration of the currently loaded track in milliseconds
	 */
	int getDuration();

	/**
	 * Adds a {@link PlaybackPositionUpdateListener} to this de.hdm_tunes2.mediaplayer.player. This
	 * listener will regularly be informed about position updates while the
	 * de.hdm_tunes2.mediaplayer.player is playing.
	 * 
	 * @param listener
	 *            the listener to add
	 */
	void addPlaybackPositionUpdateListener(
			PlaybackPositionUpdateListener listener);

	/**
	 * Removes a {@link PlaybackPositionUpdateListener} from this de.hdm_tunes2.mediaplayer.player.
	 * 
	 * @param listener
	 *            the listener to remove
	 */
	void removePlaybackPositionUpdateListener(
			PlaybackPositionUpdateListener listener);

	/**
	 * Adds a {@link PlaybackCompletionListener} to this de.hdm_tunes2.mediaplayer.player.
	 * 
	 * @param listener
	 *            the listener to add
	 */
	void addPlaybackCompleteListener(PlaybackCompletionListener listener);

	/**
	 * Removes a {@link PlaybackCompletionListener} from this de.hdm_tunes2.mediaplayer.player.
	 * 
	 * @param listener
	 *            the listener to remove
	 */
	void removePlaybackCompleteListener(PlaybackCompletionListener listener);

	/**
	 * Adds a {@link TrackLoadedListener} to this de.hdm_tunes2.mediaplayer.player.
	 * 
	 * @param listener
	 *            the listener to add
	 */
	void addTrackLoadedListener(TrackLoadedListener listener);

	/**
	 * Removes a {@link TrackLoadedListener} from this de.hdm_tunes2.mediaplayer.player.
	 * 
	 * @param listener
	 *            the listener to remove
	 */
	void removeTrackLoadedListener(TrackLoadedListener listener);
}
