package com.aveson.artist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = findViewById(R.id.appTitle);
        TextView subtitle = findViewById(R.id.appSubtitle);
        Button getStartedButton = findViewById(R.id.getStartedButton);

        title.setText(R.string.app_name);
        subtitle.setText(R.string.welcome_subtitle);

        getStartedButton.setOnClickListener(v ->
                showMessage("The AVESON ARTIST foundation is ready.")
        );
    }

    private void showMessage(String message) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show();
    }
}
