package com.programasoft.drinkshop;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.programasoft.drinkshop.Utils.Common;
import com.programasoft.drinkshop.model.checkExistUserResponse;
import com.programasoft.drinkshop.model.user;
import com.programasoft.drinkshop.retrofit.IDrinkShopApi;

import java.security.MessageDigest;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
private final int REQUEST_CODE=1;
private IDrinkShopApi mService;
    android.app.AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mService= Common.getApi();

        //check Session

        if(AccountKit.getCurrentAccessToken()!=null)
        {   dialog=new SpotsDialog.Builder().setContext(this).build();
            dialog.setMessage("Waiting...");
            dialog.show();
            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                mService.checkExistUser(account.getPhoneNumber().toString()).enqueue(new Callback<checkExistUserResponse>() {
                    @Override
                    public void onResponse(Call<checkExistUserResponse> call, Response<checkExistUserResponse> response) {
                        checkExistUserResponse existUserResponse=response.body();
                        if(existUserResponse.isExiste())
                        { //IF user existe
                            dialog.dismiss();
                            mService.getUserInformation(account.getPhoneNumber().toString()).enqueue(new Callback<user>() {
                                @Override
                                public void onResponse(Call<user> call, Response<user> response) {
                                    Common.CorrentUser=response.body();

                                    StartMainActivity();
                                }

                                @Override
                                public void onFailure(Call<user> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),t.getMessage()+"h",Toast.LENGTH_LONG).show();
                                }
                            });


                        }else
                        {   dialog.dismiss();
                            Rgister(account.getPhoneNumber().toString());
                        }

                    }

                    @Override
                    public void onFailure(Call<checkExistUserResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onError(AccountKitError accountKitError) {
                Log.d("EROOR",accountKitError.getErrorType().getMessage());
                dialog.dismiss();
            }
        });

        }



    }

    public void CONTINUE(View view) {

        StartLoginPage(LoginType.PHONE);

    }


    private void StartLoginPage(LoginType loginType)
    {
        Intent i=new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder builder=new AccountKitConfiguration.AccountKitConfigurationBuilder(loginType,AccountKitActivity.ResponseType.TOKEN);
        i.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,builder.build());
        this.startActivityForResult(i,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE)
        {
            AccountKitLoginResult result=data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);

            if(result.getError()!=null)
            {
                Toast.makeText(this,result.getError().getErrorType().getMessage(),Toast.LENGTH_LONG).show();
            }else if(result.wasCancelled())
            {
                Toast.makeText(this,"Login Cancel",Toast.LENGTH_LONG).show();
            }else
            {     if(result.getAccessToken()!=null)
                  {

                      dialog=new SpotsDialog.Builder().setContext(this).build();
                      dialog.setMessage("Waiting...");
                      dialog.show();
                     //Get user Phone and chek ixiste in basedonne
                      AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                          @Override
                          public void onSuccess(final Account account) {
                              mService.checkExistUser(account.getPhoneNumber().toString()).enqueue(new Callback<checkExistUserResponse>() {
                                  @Override
                                  public void onResponse(Call<checkExistUserResponse> call, Response<checkExistUserResponse> response) {
                                      checkExistUserResponse existUserResponse=response.body();
                                      if(existUserResponse.isExiste())
                                      { //IF user existe
                                          dialog.dismiss();
                                                mService.getUserInformation(account.getPhoneNumber().toString()).enqueue(new Callback<user>() {
                                                    @Override
                                                    public void onResponse(Call<user> call, Response<user> response) {

                                                        Common.CorrentUser= response.body();;
                                                        StartMainActivity();
                                                    }

                                                    @Override
                                                    public void onFailure(Call<user> call, Throwable t) {
                                                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                                                    }
                                                });


                                      }else
                                      {   dialog.dismiss();
                                          Rgister(account.getPhoneNumber().toString());
                                      }

                                  }

                                  @Override
                                  public void onFailure(Call<checkExistUserResponse> call, Throwable t) {
                                      Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                                  }
                              });
                          }

                          @Override
                          public void onError(AccountKitError accountKitError) {
                          Log.d("EROOR",accountKitError.getErrorType().getMessage());
                          }
                      });


                  }else
                  { Toast.makeText(this,"Success:" + result.getAuthorizationCode(),Toast.LENGTH_LONG).show();

                  }

            }
        }
    }


private void  StartMainActivity(){
        Intent i=new Intent(this,HomeActivity.class);
        this.startActivity(i);
        this.finish();

}
private void Rgister(final String phone)
{
    AlertDialog.Builder alertDialog= new AlertDialog.Builder(this);
    alertDialog.setTitle("Register");
    LayoutInflater  inflater=this.getLayoutInflater();
    View v=inflater.inflate(R.layout.register_layout,null);

    final TextInputEditText txt_name=(TextInputEditText)v.findViewById(R.id.txt_name);
    final TextInputEditText txt_birthdate=(TextInputEditText)v.findViewById(R.id.txt_birthdate);
    final TextInputEditText txt_address=(TextInputEditText)v.findViewById(R.id.txt_address);
    Button btn_Continue=(Button)v.findViewById(R.id.btn_continue);
    alertDialog.setView(v);
    final AlertDialog  dialog=alertDialog.show();
    dialog.setCancelable(false);
    btn_Continue.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name=txt_name.getText().toString();
            String birthdate=txt_birthdate.getText().toString();
            String address=txt_address.getText().toString();
            if(name.isEmpty()==false && birthdate.isEmpty() ==false && address.isEmpty()==false) {
                mService.RegisterUser(phone, name, birthdate, address).enqueue(new Callback<user>() {
                    @Override
                    public void onResponse(Call<user> call, Response<user> response) {
                        dialog.dismiss();
                        user user = response.body();
                        Toast.makeText(getApplicationContext(), user.getAddress() + "   " + user.getName(), Toast.LENGTH_LONG).show();
                        Common.CorrentUser=user;
                        StartMainActivity();
                    }

                    @Override
                    public void onFailure(Call<user> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        }
    });

}




}
