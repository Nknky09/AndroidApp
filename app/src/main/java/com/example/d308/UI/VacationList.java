package com.example.d308.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308.R;
import com.example.d308.database.Repository;
import com.example.d308.entities.Excursion;
import com.example.d308.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VacationList extends AppCompatActivity {
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);

            }
        });

        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        repository=new Repository(getApplication());
        List<Vacation> allVacations=repository.getAllVacations();
        final VacationAdapter vacationAdapter=new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);

        //System.out.println(getIntent().getStringExtra("test"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.sample) {
            repository=new Repository(getApplication());
            //Toast.makeText(ProductList.this,"put in sample data", Toast.LENGTH_LONG).show();
            Vacation vacation=new Vacation(0, "Paris", 500.00, "Palais Royale", "10/07/24", "10/14/24");
            repository.insert(vacation);

            vacation = new Vacation(0, "Nice", 300.0, "Airbnb", "10/07/24", "10/14/24" );
            repository.insert(vacation);
            Excursion excursion=new Excursion(0, "Wine Tasting", 100, 1, "10/10/24");
            repository.insert(excursion);

            excursion=new Excursion(0, "Riveira Cruise", 150, 1, "10/08/24");
            repository.insert(excursion);

            vacation = new Vacation(0, "Marseille", 400.00, "Hotel Dieu", "10/07/24", "10/14/24");
            repository.insert(vacation);

            excursion = new Excursion(0, "Fishing Tour", 100.00, 1, "10/09/24");
            repository.insert(excursion);

            return true;
        }
        if(item.getItemId()==android.R.id.home) {
            this.finish();
            return true;
        }
        return true;
    }

    @Override
    protected void onResume() {

        super.onResume();
        List<Vacation> allVacations=repository.getAllVacations();
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        final VacationAdapter vacationAdapter=new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }
}