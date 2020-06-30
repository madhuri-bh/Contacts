package com.example.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class AddContactsActivity extends AppCompatActivity {
    private EditText name;
    private EditText phone;
    private EditText email;
    private EditText age;
    RadioGroup radioGroup;
    RadioButton radioButton, rdMale, rdFemale, rdOthers;
    private EditText city;
    private EditText college;
    int gender;
    TextView genderTextView;

    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_NAME = "extra_name";
    public static final String EXTRA_PHONE_NO = "extra_phoneNo";
    public static final String EXTRA_EMAIL = "extra_task_priority";
    public static final String EXTRA_AGE = "extra_age";
    public static final String EXTRA_GENDER = "extra_gender";
    public static final String EXTRA_CITY = "extra_city";
    public static final String EXTRA_COLLEGE = "extra_college";

    public static final int GENDER_MALE = 0;
    public static final int GENDER_FEMALE = 1;
    public static final int GENDER_OTHERS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);


        final EditText name = findViewById(R.id.newName);
        //final TextView phoneText = findViewById(R.id.new_phone_text);
        final EditText phone = findViewById(R.id.newPhone);
        final EditText email = findViewById(R.id.new_email);
        final EditText age = findViewById(R.id.new_age);
        final RadioGroup radioGroup = findViewById(R.id.new_gender);
        final RadioButton rdMale = findViewById(R.id.new_male);
        final RadioButton rdFemale = findViewById(R.id.new_female);
        final RadioButton rdOthers = findViewById(R.id.new_others);
        final EditText city = findViewById(R.id.new_city);
        final EditText college = findViewById(R.id.new_college);

        //radioGroup = findViewById(R.id.new_gender);
        //genderTextView = findViewById(R.id.text_view_selected);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            name.setText(intent.getStringExtra(EXTRA_NAME));
            phone.setText(intent.getStringExtra(EXTRA_PHONE_NO));
            email.setText(intent.getStringExtra(EXTRA_EMAIL));
            age.setText(intent.getStringExtra(EXTRA_AGE));
            genderTextView.setText(intent.getStringExtra(EXTRA_GENDER));
            city.setText(intent.getStringExtra(EXTRA_CITY));
            college.setText(intent.getStringExtra(EXTRA_COLLEGE));
        } else {
            setTitle("Add Contact");
        }
    }

    private void saveContacts() {
        String Name = name.getText().toString();
        String Phone = phone.getText().toString();
        String Email = email.getText().toString();
        //String Age = age.getText().toString();
        int Age = Integer.parseInt(String.valueOf(age));
        //int Gender = Integer.parseInt(String.valueOf(gender));
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);
        genderTextView.setText(radioButton.getText());
        String City = city.getText().toString();
        String College = college.getText().toString();

        if (!Name.trim().isEmpty() || !Phone.trim().isEmpty() || !Email.trim().isEmpty() && !City.trim().isEmpty() && !College.trim().isEmpty()) {
            Toast.makeText(this, "Missed an input", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, Name);
        data.putExtra(EXTRA_PHONE_NO, Phone);
        data.putExtra(EXTRA_EMAIL, Email);
        data.putExtra(EXTRA_AGE, Age);
        data.putExtra(EXTRA_GENDER, selectedId);
        data.putExtra(EXTRA_CITY, City);
        data.putExtra(EXTRA_COLLEGE, College);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1) {
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK, data);
        finish();
        //startActivityForResult(intent,UPDATE_DATA_REQUEST_CODE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_contacts_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_contacts:
                saveContacts();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

       /* RadioGroup radioGroupGender = (RadioGroup) findViewById(R.id.new_gender);
        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.new_male) {
                    gender = GENDER_MALE;
                } else if (i == R.id.new_female) {
                    gender = GENDER_FEMALE;
                } else {
                    gender = GENDER_OTHERS;
                }
            }
        });
    }*/


        /*saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                genderTextView.setText("Gender"+radioButton.getText());
            }
        });*/


        //Button saveBtn = findViewById(R.id.new_save);
        /*saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent replyIntent = new Intent();
                String Name = name.getText().toString();
                String Phone = phone.getText().toString();
                String Email = email.getText().toString();
                //String Age = age.getText().toString();
                int Age = Integer.parseInt(String.valueOf(age));


                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                genderTextView.setText("radioButton.getText()");

                String City = city.getText().toString();
                String College = college.getText().toString();

        });
    }*/






