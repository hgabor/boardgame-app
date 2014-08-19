package hu.level14.boardgameapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

class StatelessFragment extends Fragment implements XmlClickable {
    int layoutId;
    
    public StatelessFragment(int layoutId) {
        this.layoutId = layoutId;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(this.layoutId, container, false);
        return rootView;
    }

    @Override
    public void onClick(View source) {
        // By default, do nothing
    }
}
