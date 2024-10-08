README.md

WGU D308 - Kosei Shido
Student ID - 011659212

## README file for D308 Mobile Application PA Task

- Title: France Vacations 2024
- Purpose: A Vacation and Excursions App, with prices, hotels, dates, etc.

## How to use the application

- Main Activity Page
    Displays the title, an image of France, and a Button linking to the Vacations list.
- Vacation List
    Displays the list of saved vacations, see the details by clicking on a vacation.
    Clicking on Add Sample Code from top right menu loads saved Vacations & Excursions.
    Bottom right button enables user to create as many vacations desired.
- Vacation Details
    Displays detailed information of each saved vacation, can add, update or delete any of them.
    Start date and End date validation logic, displays current dates, date picker, contain date range for add on excursions.
    Share feature, sends a message via SMS, email, link, etc.
    Alert notification and alert display box for Vacation start and end dates.
    Displays a list of all associated excursions.
    Bottom right button enables user to create excursions.
- Excursion Details
    Displays detailed information of the excursion, title, price, date, and a list of all saved vacations by name.
    User is able to add, update, or delete any excursion.
    Validation logic for excursion date, can only choose dates within the vacation date range.
    Choose desired vacation name to add or edit the associated excursion.
    Alert notification and alert display box for Excursion date, shows title. Excursion has to be saved to show alert title.

- activity_main
    Layout of the home or first page of the app.
- activity_vacation_list
    Layout of saved vacation list display
- menu_vacation_list
    Add sample vacations
- vacation_list_item
    List of vacations that are in the database.
- activity_vacation_details
    Layout for vacation details, editor and associated excursions display
- menu_vacationdetails
    Options to save, update, delete a vacation.
    Share vacation details.
    Set an alert for the vacation start and end dates.
- activity_excursion_details
    Layout for excursion details
- excursion_list_item
    List of excursions that are in the database.
- menu_excursiondetails
    Options to save, update and delete an excursion.
    Set an alert for the excursion date.

## Android Version Compatibility

- Target SDK: Android 14 - API level 35
- Minimum SDK: Android 9.0 (Pie) - API level 28
    
## Git Repository Link

- https://gitlab.com/wgu-gitlab-environment/student-repos/kshido1/d308-mobile-application-development-android.git