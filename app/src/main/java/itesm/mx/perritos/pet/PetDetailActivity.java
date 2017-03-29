package itesm.mx.perritos.pet;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.os.Bundle;

import itesm.mx.perritos.R;


public class PetDetailActivity extends AppCompatActivity {

    private ImageView ivPet;

    private Toolbar tlToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);
        ivPet = (ImageView) findViewById(R.id.image_pet);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Pet pet = (Pet) bundle.getSerializable("Pet");
//            Log.d("DEBUG_TAG","Pet image: " + pet.getIdImage());
//            ivPet.setImageResource(pet.getIdImage());
        }

        tlToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tlToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mascota");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail,menu);
        return true;
    }
}
