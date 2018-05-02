package leonardo_soares_santos.projetochatbot8bits.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import leonardo_soares_santos.projetochatbot8bits.DAO.ChatMessage;
import leonardo_soares_santos.projetochatbot8bits.R;
/**
 * Created by Leonardo Soares on 02/05/18.
 * ra 816114026
 */
public class ChatActivity extends AppCompatActivity {

    public ListView listView;
    private ListView mListView;
    private ImageView mButtonSend;
    private EditText mEditTextMessage;
    private ImageView mImageView;
    private ChatMessageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        String chaveNome = intent.getStringExtra(LoginActivity.NOME);
        // Toast toast = Toast.makeText( this,chaveNome,Toast.LENGTH_LONG);
        //  toast.show();
        String chaveEmail = intent.getStringExtra(LoginActivity.EMAIL);
        ///  EditText editText=(EditText) findViewById(R.id.editText2);
        TextView textView1=(TextView) findViewById(R.id.textView2);
        TextView textView2=(TextView) findViewById(R.id.textView3);
        // TextView textView3=(TextView) findViewById(R.id.textView4);


        //  editText.setText("Nome :"+chaveNome+"   Email :"+chaveEmail);
        textView1.setText("Olá "+chaveNome+", seja bem vindo(a) ao ChatBot.");
        textView2.setText("Use uma frase simples e objetiva para tirar suas dúvidas. Vamos lá!");
        // textView3.setText("Email: "+chaveEmail);

        // listView = (ListView) findViewById(R.id.listView);

        mListView = (ListView) findViewById(R.id.listView);
        mButtonSend = (ImageView) findViewById(R.id.iv_image2);
        mEditTextMessage = (EditText) findViewById(R.id.et_message);
        mImageView = (ImageView) findViewById(R.id.iv_image);
        mAdapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        mListView.setAdapter(mAdapter);


        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mEditTextMessage.getText().toString();
                sendMessage(message);
                mEditTextMessage.setText("");
                mListView.setSelection(mAdapter.getCount() - 1);
            }
        });
    }
    private void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, true, false);
        mAdapter.add(chatMessage);
        //respond as Helloworld


        switch (message){
            case "oi":
                mimicOtherMessage("Olá,como vai?");
                break;
            case "estou bem":
                mimicOtherMessage("que bom fico feliz!!");
                break;

            case "estou mau":
                mimicOtherMessage("problema seu!!");
                break;

            case "qual seu nome?":
                mimicOtherMessage("AlexDex !!");
                break;

            case "o que vc faz?":
                mimicOtherMessage("facilito sua vida!!");
                break;

            case "adeus":
                mimicOtherMessage("Até a proxima!!");
                break;

            case "vamos conseguir terminar esse chatbot?":
                mimicOtherMessage("depende,vc acredita em milagres meu caro?");
                break;

            case "teste":
                mimicOtherMessage("estou vivooo");
                break;


        }

    }
    private void mimicOtherMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, false, false);
        mAdapter.add(chatMessage);
    }
    private void sendMessage() {
        ChatMessage chatMessage = new ChatMessage(null, true, true);
        mAdapter.add(chatMessage);

        mimicOtherMessage();
    }
    private void mimicOtherMessage() {
        ChatMessage chatMessage = new ChatMessage(null, false, true);
        mAdapter.add(chatMessage);
    }


        /*listView.setText("Olá " + chaveNome + ", seja bem vindo ao ChatBot. " +
                "Use uma frase simples e objetiva para tirar suas dúvidas. Vamos lá! ");*/


}
