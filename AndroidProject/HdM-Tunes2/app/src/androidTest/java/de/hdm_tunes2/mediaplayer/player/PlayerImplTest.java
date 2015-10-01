/**
 * Created on 25.02.2011
 *
 * © 2011 Daniel Thommes
 */
package de.hdm_tunes2.mediaplayer.player;

import android.net.Uri;
import android.test.AndroidTestCase;
import de.hdm_tunes2.mediaplayer.data.Track;
import de.hdm_tunes2.mediaplayer.player.Player.PlayerState;

/**
 * Test for the {@link PlayerImpl}.
 * 
 * @author
 */
public class PlayerImplTest extends AndroidTestCase {

	private Player player;
	private Track testTrack;
	private volatile int playbackPositionValue = 0;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		//player = new PlayerImpl();
		player = new PlayerImpl(getContext());

		testTrack = new Track();
		testTrack.setUri(Uri
				.parse("android.resource://de.hdm_tunes2/raw/test1"));
	}

	/**
	 * @throws Exception
	 */
	public void testPlayAndPause() throws Exception {

		assertEquals(PlayerState.empty, player.getState());

		player.load(testTrack);

		assertEquals(PlayerState.paused, player.getState());

		player.play();

		assertEquals(PlayerState.playing, player.getState());

		Thread.sleep(1000);

		player.pause();

		Thread.sleep(1000);

		assertEquals(PlayerState.paused, player.getState());

		player.play();

		assertEquals(PlayerState.playing, player.getState());

		Thread.sleep(1000);

		player.load(testTrack);

		assertEquals(PlayerState.playing, player.getState());

		Thread.sleep(1000);

		player.pause();
	}

	/**
	 * @throws Exception
	 */
	public void testPlayPositionUpdate() throws Exception {

		player.addPlaybackPositionUpdateListener(new PlaybackPositionUpdateListener() {
			public void onPlaybackPositionUpdate(int playbackPosition,
					int duration) {
				playbackPositionValue = playbackPosition;
			}
		});

		player.load(testTrack);

		player.play();

		Thread.sleep(115000);
/*
		assertTrue("Playbackposition is not >= 3000",
				playbackPositionValue >= 3000);

		player.pause();*/
	}

}
