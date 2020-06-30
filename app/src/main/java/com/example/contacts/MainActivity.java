package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private final static int NEW_DATA_REQUEST_CODE = 1;
    private final static int UPDATE_DATA_REQUEST_CODE = 2;

    public static final int GENDER_OTHERS = 3;
    public static final int GENDER_MALE = 4;
    public static final int GENDER_FEMALE = 5;

    private ContactsViewModel mViewModel;
    private Contacts contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton = findViewById(R.id.button_add_contacts);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContactsActivity.class);
                startActivityForResult(intent, NEW_DATA_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setHasFixedSize(true);
        final ContactsAdapter adapter = new ContactsAdapter();
        recyclerView.setAdapter(adapter);

        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        mViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        mViewModel.pagedListLiveData.observe(this, new Observer<PagedList<Contacts>>() {
            @Override
            public void onChanged(@Nullable final PagedList<Contacts> contacts) {
                adapter.setContacts(contacts);
            }
        });

        /*ConstraintLayout constraint = findViewById(R.id.Constraint_Layout);
        final Snackbar snackbar = Snackbar.make(constraint,"Task Deleted", BaseTransientBottomBar.LENGTH_SHORT)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewModel.insert(contacts);
                    }
                });*/

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
               mViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
               Toast.makeText(MainActivity.this, "Contact deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ContactsAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Contacts contacts) {
                Intent intent = new Intent(MainActivity.this, AddContactsActivity.class);
                intent.putExtra(AddContactsActivity.EXTRA_ID, contacts.getId());
                intent.putExtra(AddContactsActivity.EXTRA_NAME, contacts.getName());
                intent.putExtra(AddContactsActivity.EXTRA_PHONE_NO, contacts.getPhoneNo());
                intent.putExtra(AddContactsActivity.EXTRA_EMAIL, contacts.getEmail());
                intent.putExtra(AddContactsActivity.EXTRA_AGE, contacts.getAge());
                intent.putExtra(AddContactsActivity.EXTRA_GENDER, contacts.getGender());
                intent.putExtra(AddContactsActivity.EXTRA_CITY, contacts.getCity());
                intent.putExtra(AddContactsActivity.EXTRA_COLLEGE, contacts.getCollege());
                startActivityForResult(intent, UPDATE_DATA_REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_DATA_REQUEST_CODE  && resultCode == RESULT_OK) {
                String name = data.getStringExtra(AddContactsActivity.EXTRA_NAME);
                String phone = data.getStringExtra(AddContactsActivity.EXTRA_PHONE_NO);
                String email = data.getStringExtra(AddContactsActivity.EXTRA_EMAIL);
                int age = data.getIntExtra(AddContactsActivity.EXTRA_AGE, 1);
                int gender = data.getIntExtra(AddContactsActivity.EXTRA_GENDER, GENDER_MALE);
                String city = data.getStringExtra(AddContactsActivity.EXTRA_CITY);
                String college = data.getStringExtra(AddContactsActivity.EXTRA_COLLEGE);

                Contacts contacts = new Contacts(name, phone, email, age, gender, city, college);
                mViewModel.insert(contacts);
                Toast.makeText(this, "Added Contact", Toast.LENGTH_SHORT).show();
            } else if ( requestCode == UPDATE_DATA_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddContactsActivity.EXTRA_ID, -1);

            if(id == -1) {
                Toast.makeText(this,"Contacts cannot be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddContactsActivity.EXTRA_NAME);
            String phone = data.getStringExtra(AddContactsActivity.EXTRA_PHONE_NO);
            String email = data.getStringExtra(AddContactsActivity.EXTRA_EMAIL);
            int age = data.getIntExtra(AddContactsActivity.EXTRA_AGE, 1);
            int gender = data.getIntExtra(AddContactsActivity.EXTRA_GENDER, GENDER_MALE);
            String city = data.getStringExtra(AddContactsActivity.EXTRA_CITY);
            String college = data.getStringExtra(AddContactsActivity.EXTRA_COLLEGE);


            Contacts contacts = new Contacts(name, phone, email, age, gender, city, college);
            contacts.setId(id);
            mViewModel.update(contacts);

            Toast.makeText(this, "Contact Updated", Toast.LENGTH_SHORT).show();

        } else {
                Toast.makeText(this, "Cancelled Added Contact", Toast.LENGTH_SHORT).show();
            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_contacts:
                mViewModel.deleteContacts();
                Toast.makeText(this, "All contacts deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

