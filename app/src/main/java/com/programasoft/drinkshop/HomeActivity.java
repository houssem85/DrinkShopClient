package com.programasoft.drinkshop;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.AccountKit;
import com.nex3z.notificationbadge.NotificationBadge;
import com.programasoft.drinkshop.Adapter.MenuAdapter;
import com.programasoft.drinkshop.Database.DataSource.CartRepository;
import com.programasoft.drinkshop.Database.Local.CartDataSource;
import com.programasoft.drinkshop.Database.Local.Database;
import com.programasoft.drinkshop.model.menu;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.programasoft.drinkshop.Utils.Common;
import com.programasoft.drinkshop.model.banner;
import com.programasoft.drinkshop.retrofit.IDrinkShopApi;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int CODE_PERMISSION_STOREDGE =1001 ;
    private static final int CODE_GALLERY =5000 ;
    private TextView txt_name;
    private TextView txt_phone;
    private SliderLayout sliderLayout;
    private IDrinkShopApi mService;
    private CompositeDisposable compositeDisposable;
    private RecyclerView menus;
    private NotificationBadge badge;
    private CircleImageView image_profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ////// inisialisaion des variable
        View headerView=navigationView.getHeaderView(0);
        txt_name=(TextView)headerView.findViewById(R.id.txt_name);
        txt_phone=(TextView)headerView.findViewById(R.id.txt_phone);
        image_profil=(CircleImageView)headerView.findViewById(R.id.imageView);
        Picasso.with(this).load(Common.BASE_URL+"user_avar/"+Common.CorrentUser.getAvat()).into(image_profil);
        image_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_PICK);
                HomeActivity.this.startActivityForResult(Intent.createChooser(i,"Select Picture"),CODE_GALLERY);
            }
        });
        txt_name.setText(Common.CorrentUser.getName());
        txt_phone.setText(Common.CorrentUser.getPhone());
        sliderLayout=(SliderLayout)this.findViewById(R.id.slider);
        mService=Common.getApi();
        menus=(RecyclerView)this.findViewById(R.id.list_menu);
        menus.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mService.GetBanners().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<banner>>() {
            @Override
            public void accept(List<banner> banners) throws Exception {


                for(banner b:banners) {
                    TextSliderView textSliderView=new TextSliderView(getApplicationContext());
                    textSliderView.description(b.getName()).image(b.getLink()).setScaleType(BaseSliderView.ScaleType.Fit);
                    sliderLayout.addSlider(textSliderView);

                }


            }
        });

        mService.GetMenu().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<menu>>() {
            @Override
            public void accept(List<menu> menus_list) throws Exception {
            MenuAdapter menuAdapter=new MenuAdapter(getApplicationContext(),menus_list);
            menus.setAdapter(menuAdapter);

            }
        });

     //Ack permission for read storedg
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE},CODE_PERMISSION_STOREDGE);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK) {
            if (requestCode == CODE_GALLERY) {
                if (data != null) {
                    Uri ImageURI = data.getData();

                    if(ImageURI.getPath().isEmpty()==false) {
                        String filePath = getRealPathFromUri(ImageURI);
                        final File image_Chooset = new File(filePath);

                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image_Chooset);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", Common.CorrentUser.getPhone()+".jpg", requestFile);
                        RequestBody phone = RequestBody.create(MediaType.parse("multipart/form-data"), Common.CorrentUser.getPhone());
                        mService.UploadAvar(phone,body).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Toast.makeText(getApplicationContext(),response.body(),Toast.LENGTH_LONG).show();
                                Picasso.with(getApplicationContext()).load(Common.BASE_URL+"user_avar/"+Common.CorrentUser.getPhone()+".jpg").into(image_profil);

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                             Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });

                    }else
                    {
                        Toast.makeText(this,"Cannot upload this file to server",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CODE_PERMISSION_STOREDGE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {Toast.makeText(this,"Permission granted",Toast.LENGTH_LONG).show();
            }else
            {Toast.makeText(this,"Permission denied",Toast.LENGTH_LONG).show();
            }

        }
    }
    boolean IsBackButtonPressed=false;
    @Override
    public void onBackPressed() {

        if(IsBackButtonPressed)
        {super.onBackPressed();
         return;
        }else {
            IsBackButtonPressed=true;
            Toast.makeText(this,"Please Click back Again to exit",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        View count = menu.findItem(R.id.badge).getActionView();
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),CartActivity.class);
                getApplicationContext().startActivity(i);
            }
        });
        badge = (NotificationBadge) count.findViewById(R.id.badge);
        Database database= Database.getInstance(this);
        final CartRepository repository= CartRepository.getInstance(CartDataSource.getInstance(database.cartDao()));
        badge.setText(String.valueOf(repository.getCartNumbers()));
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AccountKit.logOut();
            finish();
            this.startActivity(new Intent(this,MainActivity.class));
            return true;
        }else if(id==R.id.search)
        {
            this.startActivity(new Intent(this,SearchsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id==R.id.log_out)
        {   AccountKit.logOut();
            finish();
            this.startActivity(new Intent(this,MainActivity.class));

        }else {
            Intent i=new Intent(HomeActivity.this,FavoriteActivity.class);
            HomeActivity.this.startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        IsBackButtonPressed=false;
        if(badge!=null) {
            Database database = Database.getInstance(this);
            final CartRepository repository = CartRepository.getInstance(CartDataSource.getInstance(database.cartDao()));
            badge.setText(String.valueOf(repository.getCartNumbers()));
        }
        }
    public String getRealPathFromUri(final Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(this, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(this, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(this, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private String getDataColumn(Context context, Uri uri, String selection,
                                 String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }



}
