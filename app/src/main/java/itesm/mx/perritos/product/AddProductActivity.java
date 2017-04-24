package itesm.mx.perritos.product;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import itesm.mx.perritos.R;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mProductsPhotosStorageReference;

    private String selectedImage;

    private Product product;

    private static final int RC_PHOTO_PICKER = 1;

    private EditText editNombre;
    private EditText editPrecio;
    private CheckBox checkVisible;
    private ImageView imgPicture;
    private Button btnPicture;
    private Button btnDeleted;

    private boolean isEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        editNombre = (EditText) findViewById(R.id.edit_nombre);
        editPrecio = (EditText) findViewById(R.id.edit_precio);
        checkVisible = (CheckBox) findViewById(R.id.check_visible);
        imgPicture = (ImageView) findViewById(R.id.image_cover);
        btnPicture = (Button) findViewById(R.id.button_picture);
        btnDeleted = (Button) findViewById(R.id.button_delete);
        btnPicture.setOnClickListener(this);
        btnDeleted.setOnClickListener(this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mProductsPhotosStorageReference = mFirebaseStorage.getReference().child("products_photo");
        product = new Product();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isEditing = bundle.getBoolean("isEditing");
            getSupportActionBar().setTitle("Editar product");
            Product product1 = (Product) bundle.getSerializable("Product");
            editNombre.setText(product1.getsName());
            editPrecio.setText(String.valueOf(product1.getdPrice()));
            Glide.with(imgPicture.getContext()).load(product1.getPhotoUrl()).into(imgPicture);
            selectedImage = product1.getPhotoUrl();
        } else {
            getSupportActionBar().setTitle("Nuevo producto");
            btnDeleted.setVisibility(View.INVISIBLE);
        }



        selectedImage = null;

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_picture) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_PHOTO_PICKER) {
                Uri imageLink = data.getData();
                StorageReference photoRef = mProductsPhotosStorageReference.child(imageLink.getLastPathSegment());
                photoRef.putFile(imageLink).addOnSuccessListener(this,new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUri = taskSnapshot.getDownloadUrl();
                        Glide.with(imgPicture.getContext())
                                .load(downloadUri.toString())
                                .into(imgPicture);
                        selectedImage = downloadUri.toString();
                        product.setPhotoUrl(downloadUri.toString());
                    }
                });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.confirm, menu);
        return true;
    }


    private boolean isAllDataCorrect() {
        if (editNombre.getText().toString().length() == 0 || editPrecio.getText().toString().trim().length() == 0 || selectedImage == null) {
            return false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_confirm:
                if (isAllDataCorrect()) {
                    product.setsName(editNombre.getText().toString());
                    product.setdPrice(Double.valueOf(editPrecio.getText().toString()));
                    product.setPhotoUrl(selectedImage);
                    Intent intent = new Intent();
                    intent.putExtra("Product", product);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}
