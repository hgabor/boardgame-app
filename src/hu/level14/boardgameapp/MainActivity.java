package hu.level14.boardgameapp;

import java.util.List;

import hu.level14.boardgameapp.remote.GameServer;
import hu.level14.boardgameapp.remote.Session;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.R.string;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.os.Build;



public class MainActivity extends ActionBarActivity {
    Session session;
    
    public void onConnectButtonClick(View v) {
        String serverAddress = ((EditText)findViewById(R.id.edit_server_address)).getText().toString();
        String nickName = ((EditText)findViewById(R.id.edit_nick)).getText().toString();
        
        session = GameServer.NewSession(serverAddress, nickName);
        
        changeFragment(new StatelessFragment(R.layout.fragment_lobby));
    }
    
    public void onNewGameButtonClick(View v) {
        List<String> games = session.queryGameTypes();
        
        changeFragment(new StatelessFragment(R.layout.fragment_game_types));
    }
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new StatelessFragment(R.layout.fragment_select_server))
                    .commit();
        }
    }

    private void changeFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, f)
            .addToBackStack(null)
            .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
