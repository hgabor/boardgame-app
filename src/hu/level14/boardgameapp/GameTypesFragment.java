package hu.level14.boardgameapp;

import java.util.List;

import hu.level14.boardgameapp.remote.Session;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

class GameTypesFragment extends StatefulFragment {
    public GameTypesFragment(Session session) {
        super(R.layout.fragment_game_types, session);
    }

    @Override
    public void onClick(View source) {
    }
    
    private ListView currentView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        currentView = (ListView)super.onCreateView(inflater, container, savedInstanceState);
        return currentView;
    }

    @Override
    public void onResume() {
        super.onResume();

        List<String> types = this.session.queryGameTypes();

        currentView.setAdapter(new ArrayAdapter<String>(this.getActivity(),
                R.layout.listitem_textonly, types));
    }
}
