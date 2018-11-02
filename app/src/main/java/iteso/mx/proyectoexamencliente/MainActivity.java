package iteso.mx.proyectoexamencliente;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iteso.mx.proyectoexamencliente.adapters.AdapterProduct;
import iteso.mx.proyectoexamencliente.beans.Category;
import iteso.mx.proyectoexamencliente.beans.ItemProduct;
import iteso.mx.proyectoexamencliente.database.CategoryControl;
import iteso.mx.proyectoexamencliente.database.DataBaseHandler;
import iteso.mx.proyectoexamencliente.database.StoreControl;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ItemProduct> myDataSet;

    int contador = 0;

    private int selectedItem = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        myDataSet = new ArrayList<>();
        mAdapter = new AdapterProduct(this,myDataSet);
        recyclerView.setAdapter(mAdapter);

        getContents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_technology:
                if(selectedItem!=1) { borrarContents(); }

                selectedItem = 1;
                getContents();
                return true;
            case R.id.menu_item_home:
                if(selectedItem!=2) { borrarContents(); }

                selectedItem = 2;
                getContents();
                return true;
            case R.id.menu_item_electronics:
                if(selectedItem!=3) { borrarContents(); }

                selectedItem = 3;
                getContents();
                return true;
            case R.id.menu_item_refresh:
                //refresh
                borrarContents();
                getContents();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void borrarContents() {
        myDataSet.clear();
        mAdapter.notifyDataSetChanged();
    }

    private void getContents() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = null;
        Uri uri = Uri.parse("content://mx.iteso.proyectoexamen/products/"+selectedItem);

        cursor = contentResolver.query(uri, null, null, null, null, null);

        if(cursor != null) {
            //myDataSet.clear();
            DataBaseHandler dh = DataBaseHandler.getInstance(this);
            while(cursor.moveToNext()) {
                ItemProduct itemProduct = new ItemProduct();
                CategoryControl categoryControl = new CategoryControl();
                List<Category> lCategorias = categoryControl.getCategories(dh);
                Category tCategory = null;
                int idCat;
                StoreControl storeControl = new StoreControl();

                itemProduct.setCode(cursor.getInt(0));
                itemProduct.setTitle(cursor.getString(1));
                itemProduct.setImage(cursor.getInt(2));
                idCat = cursor.getInt(3);
                for(Category cat : lCategorias) {
                    if( cat.getId() == idCat ) {
                        tCategory = cat;
                        break;
                    }
                }
                itemProduct.setCategory(tCategory);
                itemProduct.setStore(storeControl.getStoreById(cursor.getInt(4), dh));

                myDataSet.add(itemProduct);
            }
            ItemProduct it = new ItemProduct();
            it.setTitle("Hola"+contador++);
            myDataSet.add(it);
            mAdapter.notifyDataSetChanged();
        }
    }
}
