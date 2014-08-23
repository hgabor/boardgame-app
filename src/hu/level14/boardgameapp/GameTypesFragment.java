package hu.level14.boardgameapp;

import java.util.List;

import hu.level14.boardgameapp.remote.Session;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
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

        currentView = (ListView) super.onCreateView(inflater, container,
                savedInstanceState);

        currentView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                if (position < gameTypes.size()) {
                    //String gameType = gameTypes.get(position);
                    ((MainActivity) getActivity())
                            .changeFragment(new WaitingForPlayersFragment(
                                    session));
                }
            }
        });

        return currentView;
    }

    private List<String> gameTypes;

    @Override
    public void onResume() {
        super.onResume();

        gameTypes = this.session.queryGameTypes();

        currentView.setAdapter(new ArrayAdapter<String>(this.getActivity(),
                R.layout.listitem_textonly, gameTypes));
    }
}
