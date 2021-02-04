package com.example.mystudyzone;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static java.lang.Integer.parseInt;


public class BasicActivity extends BaseActivity {

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with some events.
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String Date = dateFormat.format(calendar.getTime());


        //events.add(event);


        //events.add(event);


        //events.add(event);


        //events.add(event);


        //events.add(event);


        //events.add(event);


        //events.add(event);




        /*
        AppDatabase db = AppDatabase.getInstance(this);
        PeriodDao periodDao = db.periodDao();
        List<Period> periods = periodDao.getAll();
        for(int i = 0; i < periods.size(); i++){
            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.DAY_OF_MONTH,parseInt(periods.get(i).start_date.substring(8, 10)));
            startTime.set(Calendar.HOUR_OF_DAY, parseInt(periods.get(i).start_time.substring(0, 2)));
            startTime.set(Calendar.MINUTE, parseInt(periods.get(i).start_time.substring(3, 5)));
            startTime.set(Calendar.MONTH, parseInt(periods.get(i).start_date.substring(5, 7))-1);
            startTime.set(Calendar.YEAR, parseInt(periods.get(i).start_date.substring(0, 4)));
            Calendar endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR_OF_DAY, parseInt(periods.get(i).duration));

            com.example.mystudyzone.WeekViewEvent addedevent = new com.example.mystudyzone.WeekViewEvent(periods.get(i).id, periods.get(i).name,startTime, endTime, periods.get(i).name, periods.get(i).type, periods.get(i).subject );
            addedevent.setColor(getResources().getColor(R.color.event_color_01));
            events.add(addedevent);
        }

        DeadlineDao deadlineDao = db.deadlineDao();
        List<Deadline> deadlines = deadlineDao.getAll();
        for(int i = 0; i < deadlines.size(); i++){
            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.DAY_OF_MONTH,parseInt(deadlines.get(i).date.substring(8, 10)));
            startTime.set(Calendar.HOUR_OF_DAY, parseInt(deadlines.get(i).time.substring(0, 2)));
            startTime.set(Calendar.MINUTE, parseInt(deadlines.get(i).time.substring(3, 5)));
            startTime.set(Calendar.MONTH, parseInt(deadlines.get(i).date.substring(5, 7))-1);
            startTime.set(Calendar.YEAR, parseInt(deadlines.get(i).date.substring(0, 4)));
            Calendar endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR_OF_DAY, 1);

            com.example.mystudyzone.WeekViewEvent addedevent = new com.example.mystudyzone.WeekViewEvent(deadlines.get(i).id + 30, deadlines.get(i).name,startTime, endTime, deadlines.get(i).name, deadlines.get(i).category, deadlines.get(i).subject );
            addedevent.setColor(getResources().getColor(R.color.event_color_01));
            events.add(addedevent);
        }*/
        AppDatabase db = AppDatabase.getInstance(this);
        DeadlineDao deadlineDao = db.deadlineDao();
        PeriodDao periodDao = db.periodDao();
        List<Deadline> deadlines = deadlineDao.getAll();
        List<Period> Periods = periodDao.getAll();

        for(int i = 0; i < deadlines.size(); i++){
            Calendar startTime = Calendar.getInstance();
            int day_of_month = parseInt(deadlines.get(i).date.substring(8, 10));
            int hour_of_day = parseInt(deadlines.get(i).time.substring(0, 2));
            int minute = parseInt(deadlines.get(i).time.substring(3, 5));
            int month = parseInt(deadlines.get(i).date.substring(5, 7))-1;
            int year = parseInt(deadlines.get(i).date.substring(0, 4));
            startTime.set(Calendar.DAY_OF_MONTH, day_of_month);
            startTime.set(Calendar.HOUR_OF_DAY, hour_of_day);
            startTime.set(Calendar.MINUTE, minute);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR_OF_DAY, 1);


            com.example.mystudyzone.WeekViewEvent addedevent = new com.example.mystudyzone.WeekViewEvent(deadlines.get(i).id, deadlines.get(i).name,startTime, endTime, "HI HI CAPTAIN", deadlines.get(i).category, deadlines.get(i).subject );
            addedevent.setColor(getResources().getColor(R.color.event_color_01));
            events.add(addedevent);
        }
        for(int i = 0; i < Periods.size(); i++){
            Calendar startTime = Calendar.getInstance();
            int day_of_month = parseInt(Periods.get(i).start_date.substring(8, 10));
            int hour_of_day = parseInt(Periods.get(i).start_time.substring(0, 2));
            int minute = parseInt(Periods.get(i).start_time.substring(3, 5));
            int month = parseInt(Periods.get(i).start_date.substring(5, 7))-1;
            int year = parseInt(Periods.get(i).start_date.substring(0, 4));
            startTime.set(Calendar.DAY_OF_MONTH, day_of_month);
            startTime.set(Calendar.HOUR_OF_DAY, hour_of_day);
            startTime.set(Calendar.MINUTE, minute);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR_OF_DAY, parseInt(Periods.get(i).duration));


            com.example.mystudyzone.WeekViewEvent addedevent = new com.example.mystudyzone.WeekViewEvent(Periods.get(i).id, Periods.get(i).name,startTime, endTime, "HI HI CAPTAIN", Periods.get(i).type, Periods.get(i).subject);
            addedevent.setColor(getResources().getColor(R.color.event_color_01));
            events.add(addedevent);
        }
        // gets deadlines that need to be studied in general we need those of this month only

        /*boolean isthere = false;
        for (int i = 0; i < events.size(); i++){
            if(events.get(i).getName() == addedevent.getName() && events.get(i).getCategory().equals(addedevent.getCategory()))
                isthere = true;
        }
        if(!isthere)
            events.add(addedevent);*/

        /*Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH,14);
        startTime.set(Calendar.HOUR_OF_DAY, 1);
        startTime.set(Calendar.MINUTE, 15);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 1);

        com.example.mystudyzone.WeekViewEvent addedevent = new com.example.mystudyzone.WeekViewEvent(5, "Assignment 1",startTime, endTime, "i am very long", "Assignment", "physics"  );
        addedevent.setColor(getResources().getColor(R.color.event_color_01));
        events.add(addedevent);*/


        return events;
    }

}