package de.dhbw.handycrab;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import de.dhbw.handycrab.helper.ServiceProvider;
import de.dhbw.handycrab.model.Barrier;

public class DetailActivity extends AppCompatActivity {

    private Barrier activeBarrier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        activeBarrier = (Barrier) ServiceProvider.DataHolder.retrieve(BarrierListActivity.ACTIVE_BARRIER);

        ((TextView) findViewById(R.id.detail_title)).setText(activeBarrier.getTitle());
        ((TextView) findViewById(R.id.detail_description)).setText(activeBarrier.getDescription());
        ((TextView) findViewById(R.id.detail_user)).setText(String.format("%s", activeBarrier.getUserId()));
    }
}
