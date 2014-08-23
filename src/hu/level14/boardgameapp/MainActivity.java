package hu.level14.boardgameapp;

import hu.level14.boardgameapp.remote.GameServer;
import hu.level14.boardgameapp.remote.Session;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
    Session session;

    public void onConnectButtonClick(View v) {
        String serverAddress = ((EditText) findViewById(R.id.edit_server_address))
                .getText().toString();
        String nickName = ((EditText) findViewById(R.id.edit_nick)).getText()
                .toString();

        session = GameServer.NewSession(serverAddress, nickName);

        changeFragment(new StatelessFragment(R.layout.fragment_lobby));
    }

    public void onNewGameButtonClick(View v) {
        changeFragment(new GameTypesFragment(session));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container,
                            new StatelessFragment(
                                    R.layout.fragment_select_server)).commit();
        }
    }

    private XmlClickable currentClickHandler;

    private void changeFragment(Fragment f) {
        hideSoftKeyboard();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, f).addToBackStack(null).commit();
    }

    private void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
                    .getWindowToken(), 0);
        }
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
