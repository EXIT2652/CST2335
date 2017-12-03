package com.example.exit.androidlabs;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageFragment extends Fragment {

    private View view;
    private TextView message_selected_item;
    private TextView message_selected_id;
    private Button message_delete_button;
    private ChatDatabaseHelper chatDatabase;


    //Create a constructor for MessageFragment class which takes a ChatWindow object
    public MessageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for the fragment from step 4
        view = inflater.inflate(R.layout.message_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        message_selected_item = view.findViewById(R.id.message_selected);
        message_selected_id = view.findViewById(R.id.message_id_selected);
        message_delete_button = view.findViewById(R.id.message_delete_button);

        message_selected_item.setText(getArguments().getString("Message"));
        message_selected_id.setText(getArguments().getString("MessageID"));

        message_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments().getBoolean("isTablet")) {
                    chatDatabase = new ChatDatabaseHelper(getActivity());
                    chatDatabase.deleteItem(getArguments().getString("MessageID"));
                    ((ChatWindow)getActivity()).notifyChange();
                    ((ChatWindow)getActivity()).displaySQL();
                    getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();
                } else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("MessageID", getArguments().getString("MessageID"));
                    getActivity().setResult(1, resultIntent);
                    getActivity().finish();
                }
            }
        });
    }

}


