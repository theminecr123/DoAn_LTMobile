package com.DoAn_Mobile.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.DoAn_Mobile.R;

public class EditInfoActivity extends AppCompatActivity {

    private EditText editName, editDate;
    private Spinner spinner;
    private Button saveButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        editName = findViewById(R.id.editName);
        editDate = findViewById(R.id.editDate);
        spinner = findViewById(R.id.spinner);
        saveButton = findViewById(R.id.button2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editName.getText().toString();
                String date = editDate.getText().toString();
                String gender = spinner.getSelectedItem().toString();

                finish();
            }
        });
    }
}