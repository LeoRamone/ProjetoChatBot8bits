package leonardo_soares_santos.projetochatbot8bits.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import leonardo_soares_santos.projetochatbot8bits.Constants.Data;
import leonardo_soares_santos.projetochatbot8bits.CustomAdapter;
import leonardo_soares_santos.projetochatbot8bits.R;
/**
 * Created by Leonardo Soares on 02/05/18.
 * ra 816114026
 */
public class LoginActivity extends AppCompatActivity {
    public static final String NOME = "com.leonardo_soares_santos.chatbotlssfire.NOME";
    public static final String EMAIL = "com.leonardo_soares_santos.chatbotlssfire.EMAIL";
    LayoutInflater inflater1;

    int count =0;
    String email;
    String nome;

    EditText txtnome, txtemail,txtsearch;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    Data data;



    ArrayList<Data> dataArrayList;

    CustomAdapter customAdapter;

    String key;

    int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtnome = (EditText) findViewById(R.id.txt_nome);
        txtemail = (EditText)findViewById(R.id.txt_email);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("Usuario");
        key=databaseReference.push().getKey();


        dataArrayList = new ArrayList<>();

        customAdapter = new CustomAdapter(LoginActivity.this, dataArrayList);




        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Data datam = dataSnapshot.getValue(Data.class);

                dataArrayList.add(datam);

                customAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        realtimeUpdate();
        }
    public void enviarUsu(View view) {

        Intent intent = new Intent(this, ChatActivity.class);
        nome = txtnome.getText().toString();
        email = txtemail.getText().toString();
        intent.putExtra(NOME, nome);
        intent.putExtra(EMAIL, email);


        // validaCampos();
        //  startActivity(intent);



        nome = txtnome.getText().toString().trim();
        email = txtemail.getText().toString().trim();

        if (isCampoVazio(nome)){
            txtnome.requestFocus();
            txtnome.setError("Preencha o campo NOME!");

        }else
        if ( !isEmailValido(email)){
            txtemail.requestFocus();
            txtemail.setError("Email Nulo ou Invalido!");

        }

        else {


            data = new Data(databaseReference.push().getKey(), nome, email);

            databaseReference.child(data.getKey()).setValue(data);


            //  txtnome.setText("");
            //   txtemail.setText("");
            startActivity(intent);

        }


    }
    public void realtimeUpdate(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    data = dataSnapshot1.getValue(Data.class);

                    dataArrayList.add(data);

                }


                customAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void func(){

        if(count!=0){

            customAdapter = new CustomAdapter(getApplicationContext(),dataArrayList);



        }else {

            Toast.makeText(getApplicationContext(), "There is no data to show", Toast.LENGTH_SHORT).show();
        }





    }
    private void validaCampos(){

        String nome= txtnome.getText().toString();
        String email= txtemail.getText().toString();
        // Boolean res = false;
        if (isCampoVazio(nome)){
            txtnome.requestFocus();
            txtnome.setError("Preencha o campo nome!!");

        }else if ( !isEmailValido(email)){
            txtemail.requestFocus();
            txtemail.setError("Email nulo ou invalido!!");

        }


    }
    private  boolean isCampoVazio(String valor){

        boolean resultado = (TextUtils.isEmpty(valor)|| valor.trim().isEmpty() );

        return resultado;

    }
    private boolean isEmailValido(String email ){
        boolean resultado = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());

        return resultado;


    }
}
