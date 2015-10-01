package de.hdm_tunes2;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import de.hdm_tunes2.mediaplayer.data.Track;
import de.hdm_tunes2.mediaplayer.player.PlayerImpl;
import de.hdm_tunes2.mediaplayer.playlist.CurrentTrackChangedListener;
import de.hdm_tunes2.mediaplayer.playlist.PlaylistImpl;

/**
 * This class provides the interaction between the layout and the business logic of the playlist component.
 */

public class PlaylistFragment extends Fragment implements CurrentTrackChangedListener {

    private static final String TAG = "HdM-Tunes";
    private ListView albumList;
    private PlayerImpl myPlayer;
    private PlaylistImpl myPlaylist;
    private List<Track> tracks;

    /**
     * Get data from tracks collection and fill them into the list view.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        //get Player and Playlist instances
        myPlayer = PlayerImpl.getInstance(view.getContext());
        myPlaylist = PlaylistImpl.getInstance(view.getContext());

        //get Tracks from Playlist
        tracks = myPlaylist.getTracks();

        //get listView
        albumList = (ListView) view.findViewById(R.id.listView);

        //connecting the listView with our custom TracksAdapter
        TracksAdapter listAdapter = new TracksAdapter(getActivity(), tracks);
        albumList.setAdapter(listAdapter);

        //creating list clickListener
        AdapterView.OnItemClickListener onTrackClickListener = null;

        //add track changed listener to playlist instance
        myPlaylist.addCurrentTrackChangedListener(this);

        onTrackClickListener = new AdapterView.OnItemClickListener() {

            /**
             * Set click listeners for each list item.
             * If a list item is clicked, playback starts.
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Track myTrack = new Track();

                //set track info
                TextView trackUri = (TextView) view.findViewById(R.id.trackUri);
                TextView trackArtist = (TextView) view.findViewById(R.id.trackArtist);
                TextView trackTitle = (TextView) view.findViewById(R.id.trackTitle);
                TextView trackAlbum = (TextView) view.findViewById(R.id.trackAlbum);

                TextView albumCoverUri = (TextView) view.findViewById(R.id.albumCoverUri);

                myPlaylist.selectTrackAtPosition(position);

                myTrack.setUri(Uri.parse(trackUri.getText().toString()));
                myTrack.setAlbum(trackAlbum.getText().toString());
                myTrack.setArtist(trackArtist.getText().toString());
                myTrack.setTitle(trackTitle.getText().toString());
                myTrack.setAlbumArtUri(Uri.parse(albumCoverUri.getText().toString()));

                //load and play selected track
                myPlayer.load(myTrack);
                myPlayer.play();
            }
        };

        //add clickListener to the list
        albumList.setOnItemClickListener(onTrackClickListener);

        return view;
    }

    @Override
    public void onCurrentTrackChanged(Track newCurrentTrack, int position) {

    }
}
