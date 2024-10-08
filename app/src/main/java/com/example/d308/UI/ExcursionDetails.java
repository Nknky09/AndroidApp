package com.example.d308.UI;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308.R;
import com.example.d308.database.Repository;
import com.example.d308.entities.Excursion;
import com.example.d308.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {
    String title;
    Double price;
    int excursionID;
    int vacaID;
    String startVacationDate;
    String endVacationDate;
    EditText editName;
    EditText editPrice;
    TextView editDate;
    String excursionDate;
    Repository repository;
    Excursion currentExcursion;
    int numExcursions;
    Date startStartDate = null;
    Date endEndDate = null;
    boolean isValidDate;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        repository = new Repository(getApplication());
        title = getIntent().getStringExtra("name");
        excursionDate = getIntent().getStringExtra("excursionDate");
        editName = findViewById(R.id.excursionTitle);
        editName.setText(title);
        price = getIntent().getDoubleExtra("price", 0.0);
        editPrice = findViewById(R.id.excursionPrice);
        editPrice.setText(Double.toString(price));
        excursionID = getIntent().getIntExtra("id", -1);
        vacaID = getIntent().getIntExtra("vacaID", -1);
        startVacationDate = getIntent().getStringExtra("startVacationDate");
        endVacationDate = getIntent().getStringExtra("endVacationDate");
        editDate = findViewById(R.id.excursiondate);
        editDate.setText(excursionDate);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayList<Vacation> vacationArrayList = new ArrayList<>();
        vacationArrayList.addAll(repository.getAllVacations());

        ArrayAdapter<Vacation> vacationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, vacationArrayList);
        spinner.setAdapter(vacationAdapter);
        spinner.setSelection(0);

        startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();
            }
        };

        if(startVacationDate != null && endVacationDate != null) {
            try {
                startStartDate = sdf.parse(startVacationDate);
                endEndDate = sdf.parse(endVacationDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ExcursionDetails", "Received null for start or end vacation date");
        }

        if(startVacationDate == null && endVacationDate == null) {
            String currentDate = sdf.format(new Date());
            editDate.setText(currentDate);
        }

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info=editDate.getText().toString();
                if(info.equals(""))info="10/01/24";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                DatePickerDialog datePickerDialog = new DatePickerDialog(ExcursionDetails.this, startDate,
                        myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH));

                if (startStartDate != null) {
                    datePickerDialog.getDatePicker().setMinDate(startStartDate.getTime());
                }
                if (endEndDate != null) {
                    datePickerDialog.getDatePicker().setMaxDate(endEndDate.getTime());
                }
                datePickerDialog.show();
            }
        });
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.excursionsave) {
            Excursion excursion;
            if (!isValidDate(editDate.getText().toString(), "MM/dd/yy")){
                Toast.makeText(this, "Invalid date format. Please use MM/dd/yy", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (excursionID == -1) {
                if (repository.getAllExcursions().size() == 0)
                    excursionID = 1;
                else
                    excursionID = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionID() + 1;
                excursion = new Excursion(excursionID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()),
                        vacaID, editDate.getText().toString());
                repository.insert(excursion); // Changed to insert
            } else {
                excursion = new Excursion(excursionID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()),
                        vacaID, editDate.getText().toString());
                repository.update(excursion);
            }
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.excursiondelete) {
            // Find the current excursion by excursionID
            for (Excursion prod : repository.getAllExcursions()) {
                // Locate the excursion using excursionID instead of vacaID
                if (prod.getExcursionID() == excursionID) {
                    currentExcursion = prod;
                }
            }

            // If the excursion is found, delete it
            if (currentExcursion != null) {
                repository.delete(currentExcursion);
                Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionName() + " was deleted", Toast.LENGTH_LONG).show();
                this.finish(); // Close the activity after deletion
            } else {
                Toast.makeText(ExcursionDetails.this, "Excursion not found!", Toast.LENGTH_SHORT).show();
            }
            return true;
        }



        if (item.getItemId() == R.id.alert) {
            String dateFromScreen = editDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Long trigger = myDate.getTime();
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                showAlertDialogWithCallback("Excursion Alert: " + title, () -> {
                    Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
                    intent.putExtra("key", "Excursion Title: " + getIntent().getStringExtra("name"));
                    PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Method to show AlertDialog for excursion alerts
    private void showAlertDialogWithCallback(String message, Runnable onOkClicked) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excursion Alert!")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    if (onOkClicked != null) {
                        onOkClicked.run();
                    }
                })
                .setIcon(R.drawable.ic_launcher_foreground)
                .create()
                .show();
    }

    private boolean isValidDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}





