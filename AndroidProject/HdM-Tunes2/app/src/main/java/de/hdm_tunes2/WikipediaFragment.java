package de.hdm_tunes2;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import de.hdm_tunes2.mediaplayer.data.Track;
import de.hdm_tunes2.mediaplayer.playlist.PlaylistImpl;

/**
 * Created by maximilianott on 12.05.15.
 */
public class WikipediaFragment extends Fragment {

    WebView webView;
    Track currentTrack;
    PlaylistImpl myPlaylist;
    Context context;

    //Variablen für "leere" Wiki View
    String type = "text/html";
    String encoding = "utf-8";
    String text = "<h2 style='padding-top: 20dp; text-align: center;'>Play a track to view Artist info</h2>";

    /**
     * Referencing the web view in layout. Force to load the content in application and not in browser.
     * Enabling javascript to support functionalities of the system browser.
     *
     * Instanciating Playlist and getting current track.
     *
     * Get the artist from the current track for wikipedia url.
     *
     * If no track is loaded, show error page.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wikipedia,container,false);
        context = view.getContext();

        webView = (WebView) view.findViewById(R.id.WebView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        //get instance of playlist and current track
        myPlaylist = PlaylistImpl.getInstance(context);
        currentTrack = myPlaylist.getCurrentTrack();

        //if there is a currentTrack -> load corresponding wiki article ; otherwise show the "error" message
        if(currentTrack != null) {
            String artist = currentTrack.getArtist();
            artist = artist.replaceAll("\\s", "_");
            webView.loadUrl("http://de.wikipedia.org/wiki/" + artist);
        } else {
            webView.loadData(text, type, encoding);
        }
        return view;
    }
}
