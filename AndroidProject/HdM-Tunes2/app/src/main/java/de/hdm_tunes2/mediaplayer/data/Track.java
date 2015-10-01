/**
 * Created on 25.02.2011
 * 
 * © 2011 Daniel Thommes
 */
package de.hdm_tunes2.mediaplayer.data;

import android.net.Uri;

/**
 * A representation of an audio track
 * 
 * @author Daniel Thommes
 */
public class Track {

	/**
	 * Title of the track
	 */
	private String title;
	/**
	 * Name of the performing artist
	 */
	private String artist;
	/**
	 * Name of the album
	 */
	private String album;
	private int duration;
	/**
	 * Uri of the audio content
	 */
	private Uri uri;
	/**
	 * Uri of the album art
	 */
	private Uri albumArtUri;

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Track(String title, String artist, String album, Uri uri,
			Uri albumArtUri) {
		super();
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.uri = uri;
		this.albumArtUri = albumArtUri;
	}

	public Track() {
	}

	public Uri getAlbumArtUri() {
		return albumArtUri;
	}

	public void setAlbumArtUri(Uri albumArtUri) {
		this.albumArtUri = albumArtUri;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public Uri getUri() {
		return uri;
	}

	public void setUri(Uri uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		return "Track [title=" + title + ", artist=" + artist + ", album="
				+ album + ", duration=" + duration + ", uri=" + uri
				+ ", albumArtUri=" + albumArtUri + "]";
	}

}
