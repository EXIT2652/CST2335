package com.example.exit.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import static com.example.exit.androidlabs.ChatDatabaseHelper.KEY_ID;
import static com.example.exit.androidlabs.ChatDatabaseHelper.KEY_MESSAGE;
import static com.example.exit.androidlabs.ChatDatabaseHelper.TABLE_NAME;

public class ChatWindow extends Activity {

    private ChatDatabaseHelper chatDBHelper;
    private String chatWindow = ChatWindow.class.getSimpleName();

    private ListView listView;
    private EditText editText;
    private Button buttonSend;
    private ArrayList<String> chatArray;
//    String ACTIVITY_NAME = "ChatWindow";
    private ChatAdapter messageAdapter;

    class ChatAdapter extends ArrayAdapter<String>{

        public ChatAdapter(Context ctx){
            super(ctx, 0);
        }

        public int getCount(){
            return chatArray.size();
        }

        public String getItem(int position){
            return chatArray.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;

            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position));//get the string at position
            return result;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.plain_text_input);
        buttonSend = (Button) findViewById(R.id.buttonSend);
        chatArray = new ArrayList<String>();

        //in this case, "this" is the ChatWindow, which is - A Context object
        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);
//        registerForContextMenu(listView);

        //Create a temporary ChatDatabaseHelper object, which then gets a writeable database
        chatDBHelper = new ChatDatabaseHelper(this);




/*        final SQLiteDatabase db = chatDBHelper.getWritableDatabase();*/

        //Click on Send button to send text
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().equals("") == false) {
                    chatArray.add(editText.getText().toString());
                    messageAdapter.notifyDataSetChanged();//this restarts the process of getCount()/getView()

                    chatDBHelper.insertEntry(editText.getText().toString());
                    editText.setText("");
                }
            }
        });

        //Hit Enter key on keyboard to send text
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            chatArray.add(editText.getText().toString());
                            messageAdapter.notifyDataSetChanged();//this restarts the process of getCount()/getView()

                            chatDBHelper.insertEntry(editText.getText().toString());
                            editText.setText("");
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

/*        Cursor cursor = chatDBHelper.getRecords();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Log.i(chatWindow, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));

                chatArray.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                cursor.moveToNext();
            }
            Log.i(chatWindow, "Cursor's column count = " + cursor.getColumnCount());

            cursor.moveToFirst();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.i(chatWindow, "The " + i + " row is " + cursor.getColumnName(i));
            }
        }*/
    }

    private void displaySQL() {
        Cursor cursor = chatDBHelper.getRecords();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Log.i(chatWindow, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));

                chatArray.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                cursor.moveToNext();
            }
            Log.i(chatWindow, "Cursor's column count = " + cursor.getColumnCount());
        }

            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.i(chatWindow, "The " + i + " row is " + cursor.getColumnName(i));
            }

    }

    protected void onResume(){
        super.onResume();
        Log.i(chatWindow, "In onResume()");
    }

    protected void onStart(){
        super.onStart();
        chatDBHelper.openDatabase();
        displaySQL();

        Log.i(chatWindow,"In onStart()");
    }

    protected void onPause(){
        super.onPause();
        Log.i(chatWindow,"In onPause()");
    }

    protected void onStop(){
        super.onStop();
        Log.i(chatWindow,"In onStop()");
    }

    protected void onDestroy(){
        super.onDestroy();
        chatDBHelper.closeDatabase();
        Log.i(chatWindow,"In onDestroy()");
    }

}
