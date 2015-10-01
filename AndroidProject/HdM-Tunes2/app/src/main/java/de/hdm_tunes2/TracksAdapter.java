package de.hdm_tunes2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import de.hdm_tunes2.mediaplayer.data.Track;

/**
 * Created by matthias on 21.04.15.
 */
public class TracksAdapter extends ArrayAdapter<Track> {


    public TracksAdapter(Context context, List<Track> tracks) {

        super(context, 0, tracks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Track track = getItem(position);

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.track_row, parent, false);
        }

        TextView trackTitle = (TextView) convertView.findViewById(R.id.trackTitle);
        TextView trackArtist = (TextView) convertView.findViewById(R.id.trackArtist);
        TextView trackAlbum = (TextView) convertView.findViewById(R.id.trackAlbum);

        TextView trackUri = (TextView) convertView.findViewById(R.id.trackUri);

        ImageView albumCover = (ImageView) convertView.findViewById(R.id.albumCover);

        TextView albumCoverUri = (TextView) convertView.findViewById(R.id.albumCoverUri);

        trackTitle.setText(track.getTitle());
        trackArtist.setText(track.getArtist());
        trackAlbum.setText(track.getAlbum());
        trackUri.setText(track.getUri().toString());
        albumCover.setImageURI(track.getAlbumArtUri());
        albumCoverUri.setText(track.getAlbumArtUri().toString());


        return convertView;
    }
}
