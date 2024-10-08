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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308.R;
import com.example.d308.database.*;
import com.example.d308.entities.Excursion;
import com.example.d308.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {

    EditText editName;
    EditText editPrice;
    EditText editHotel;
    String name;
    static int notificationID;
    String hotel;
    double price;
    int vacationID;
    String startVacationDate;
    String endVacationDate;
    TextView editStartVacaDate;
    TextView editEndVacaDate;
    Vacation currentVacation;
    int numExcursions;
    DatePickerDialog.OnDateSetListener startVacaDate;
    DatePickerDialog.OnDateSetListener endVacaDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);

        editName = findViewById(R.id.vacation1);
        editPrice = findViewById(R.id.price1);
        editHotel = findViewById(R.id.hoteltext);
        editStartVacaDate = findViewById(R.id.startvacationdate);
        editEndVacaDate = findViewById(R.id.endvacationdate);
        name = getIntent().getStringExtra("name");
        hotel = getIntent().getStringExtra("hotel");
        price = getIntent().getDoubleExtra("price", 0.0);
        startVacationDate = getIntent().getStringExtra("startVacationDate");
        endVacationDate = getIntent().getStringExtra("endVacationDate");
        editName.setText(name);
        editHotel.setText(hotel);
        editPrice.setText(Double.toString(price));
        vacationID = getIntent().getIntExtra("id", -1);
        editStartVacaDate.setText(startVacationDate);
        editEndVacaDate.setText(endVacationDate);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Log.d("DebugTag", "name: " + name + " vacationID: " + vacationID + " Hotel: " + hotel + ", startVacationDate: " + startVacationDate + ", endVacationDate: " + endVacationDate);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacaID", vacationID);
                startActivity(intent);

            }
        });

        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this, startVacationDate, endVacationDate);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion p : repository.getAllExcursions()) {
            if (p.getVacaID() == vacationID) filteredExcursions.add(p);
        }
        excursionAdapter.setExcursions(filteredExcursions);

        if (startVacaDate == null && endVacaDate == null) {
            String currentStartDate = sdf.format(new Date());
            editStartVacaDate.setText(currentStartDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_YEAR, 1);

            String currentEndDate = sdf.format(calendar.getTime());
            editEndVacaDate.setText(currentEndDate);
        }

        startVacaDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();
            }
        };

        endVacaDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelEnd();
            }
        };

        editStartVacaDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editStartVacaDate.getText().toString();
                if (info.equals("")) info = "10/01/24";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, startVacaDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEndVacaDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editEndVacaDate.getText().toString();
                if (info.equals("")) info = "10/01/24";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(VacationDetails.this, endVacaDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMinDate(myCalendarStart.getTimeInMillis());

                datePickerDialog.show();
            }
        });
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartVacaDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndVacaDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }

    private void scheduleAlarm(AlarmManager alarmManager, long triggerTime, String message, int notificationID) {
        Intent intent = new Intent(VacationDetails.this, MyVacationReceiver.class);
        intent.putExtra("key", message);
        intent.putExtra("notification_id", notificationID);
        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, notificationID, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, sender);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.vacationsave) {
            Vacation vacation;

            if (!isValidDate(editStartVacaDate.getText().toString(), "MM/dd/yy") || !isValidDate(editEndVacaDate.getText().toString(), "MM/dd/yy")) {
                Toast.makeText(this, "Invalid date format. Please use MM/dd/yy", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (vacationID == -1) {
                if (repository.getAllVacations().size() == 0) vacationID = 1;
                else
                    vacationID = repository.getAllVacations().get(repository.getAllVacations().size() - 1).getVacationID() + 1;
                vacation = new Vacation(vacationID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), editHotel.getText().toString(),
                        editStartVacaDate.getText().toString(), editEndVacaDate.getText().toString());
                repository.insert(vacation);
                this.finish();
            } else {
                vacation = new Vacation(vacationID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), editHotel.getText().toString(),
                        editStartVacaDate.getText().toString(), editEndVacaDate.getText().toString());
                repository.update(vacation);
                this.finish();
            }
        }
        if (item.getItemId() == R.id.vacationdelete) {
            for (Vacation vaca : repository.getAllVacations()) {
                if (vaca.getVacationID() == vacationID) currentVacation = vaca;
            }

            numExcursions = 0;
            for (Excursion excursion : repository.getAllExcursions()) {
                if (excursion.getVacaID() == vacationID) ++numExcursions;
            }

            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        if (item.getItemId() == R.id.vacationshare) {
            Intent sendIntent = new Intent();
            List<Excursion> filteredExcursions = new ArrayList<>();
            for (Excursion p : repository.getAllExcursions()) {
                if (p.getVacaID() == vacationID) filteredExcursions.add(p);
            }
            StringBuilder excursionDetails = new StringBuilder();
            for (Excursion p : filteredExcursions) {
                excursionDetails.append("Excursion Name: ")
                        .append(p.getExcursionName())
                        .append(", Price: $")
                        .append(p.getPrice())
                        .append("\n");
            }
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "These are Vacation details: vacation ID: " + vacationID + ", The name of our vacation: " +
                    editName.getText().toString() + " , price: $" +
                    Double.parseDouble(editPrice.getText().toString()) + ", the hotel name: " +
                    editHotel.getText().toString() + ", this is our end date: " +
                    editEndVacaDate.getText().toString() + ", associated excursions: " + excursionDetails +
                    " Let us know what you think!");
            sendIntent.putExtra(Intent.EXTRA_TITLE, editName.getText().toString() + "EXTRA_TITLE");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }

        // Alert setup for both Start and End Dates
        if (item.getItemId() == R.id.vacationalert) {
            String startdate = editStartVacaDate.getText().toString();
            String enddate = editEndVacaDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myStartDate = null;
            Date myEndDate = null;
            try {
                myStartDate = sdf.parse(startdate);
                myEndDate = sdf.parse(enddate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                // Show dialog for start date first
                Date finalMyStartDate = myStartDate;
                Date finalMyEndDate = myEndDate;
                showAlertDialogWithCallback("Vacation Start: " + name, () -> {
                    // Once the start dialog is dismissed, schedule the alarm for the start date
                    scheduleAlarm(alarmManager, finalMyStartDate.getTime(), "Vacation Start: " + name, notificationID++, true);

                    // Show dialog for end date after the start date dialog is handled
                    showAlertDialogWithCallback("Vacation End: " + name, () -> {
                        // Once the end dialog is dismissed, schedule the alarm for the end date
                        scheduleAlarm(alarmManager, finalMyEndDate.getTime(), "Vacation End: " + name, notificationID++, false);
                    });
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Method to schedule alarms for both start and end dates
    private void scheduleAlarm(AlarmManager alarmManager, long triggerTime, String message, int notificationID, boolean showToast) {
        Intent intent = new Intent(VacationDetails.this, MyVacationReceiver.class);
        intent.putExtra("key", message);
        intent.putExtra("notification_id", notificationID);
        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, notificationID, intent, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, sender);
    }

    // Method to show AlertDialog with a callback after dismissal
    private void showAlertDialogWithCallback(String message, Runnable callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vacation Alert!")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    if (callback != null) {
                        callback.run();
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

    @Override
    protected void onResume() {

        super.onResume();
        List<Excursion> filteredExcursions = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this, startVacationDate, endVacationDate);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        for (Excursion p : repository.getAllExcursions()) {
            if (p.getVacaID() == vacationID) filteredExcursions.add(p);
        }
        excursionAdapter.setExcursions(filteredExcursions);
    }


}
