package hu.level14.boardgameapp;

import hu.level14.boardgameapp.remote.Session;

class WaitingForPlayersFragment extends StatefulFragment {

    public WaitingForPlayersFragment(Session session) {
        super(R.layout.fragment_waiting_for_players, session);
    }

}
