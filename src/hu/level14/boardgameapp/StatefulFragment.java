package hu.level14.boardgameapp;

import hu.level14.boardgameapp.remote.Session;

class StatefulFragment extends StatelessFragment {
    protected final Session session;

    public StatefulFragment(int layoutId, Session session) {
        super(layoutId);
        this.session = session;
    }
    
}
