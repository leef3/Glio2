# Glio2
Grocery List Ingredient Organizer

Functional:
Week Schedule UI / Ingredients UI / Recipe UI / Settings UI
List of Recipes

Quick Adds:
New BG for Day Objects 1050x600 ideal
Text Titles for Breakfast Lunch Dinner
Intro Sequence - Only for once new users save bool in Shared Pref (Tabbed Scroll to Right w/ Screenshots, Opacitiy 50% on square that almost fills screen. Background will be the veggies and Redleef. Button to go to next will say "Get Started")

Bugs:
Ingredients Checkbox Repeats, has to do with list loading
Navigation Drawer Icon not changing

Future (By Priority):
Add Recipe Dialog for Recipe List Add
Add Recipe ListView Dialog for WeekSchedule
Recipe Organization by Alphabetical with Quick scroll (Links to refs below)

Side Menu Spacing and Color
Settings Fragment (Partially Done) -- Next just link to Priv Policy etc
Two / Three Sets of Image Backgrounds in Settings (Have paths stored in strings for certain sets)
Splash Screen (Will now be added to Intro Sequence)

Far Future:
Add Weeks Ingredient Compiler
Explore Recipes Function

Far Far Future:
Search Results for Recipes based on frequency (how many times added) -- A value stored in the Recipe object


References:
http://stackoverflow.com/questions/8192683/how-to-create-an-alphabetical-scrollbar-displaying-all-the-letter-in-android
https://github.com/woozzu/IndexableListView/blob/master/src/com/woozzu/android/widget/IndexScroller.java

TODO:
Populate Recipe data into the Meal Dialog Fragment for real data in the spinner - Includes instantiating listview w/ data methods

Make add button add to a test list first and display in dialog

Test list then becomes a Meal object that gets recipes added, OK button confirms and executes save data method

Callback to main activity to notifyDataSetChanged
