# Test Cases for Tellurium IDE 080\_RC1 #

## Test Case ID = TIDE\_I\_001 , Description: Install Tellurium IDE on Firefox/WindowsXp ##

  1. Install FireFox 3.5+ on Windows Xp host
  1. Install Tellurium IDE from https://addons.mozilla.org/en-US/firefox/addon/217284/

Expected result: Tellurium IDE is installed with no errors and exceptions

## Test Case ID = TIDE\_I\_002 , Description: Test Record Button ##

  1. Pre-requisite: Tellurium IDE is installed in Firefox
  1. Verify that the record button is ON by default
  1. Perform simple search in Google
  1. Stop Recording by clicking the record button

Expected result: Record button is active by default

## Test Case ID = TIDE\_I\_003 , Description: Test Replay Button ##

  1. Pre-requisite: Steps sequence must be recorded
  1. Verify that the Run button is enabled
  1. Select the first step recorded
  1. Click on Run button


Expected result: Recorded sequence must be replayed

## Test Case ID = TIDE\_I\_004 , Description: Test Update Button ##

  1. Pre-requisite: Steps sequence must be recorded
  1. Click on command view tab
  1. Select a recorded command row
  1. On value text box, edit the populated value
  1. Click on update button

Expected result: Edited value must be updated in the selected row, all other rows must not be updated.

## Test Case ID = TIDE\_I\_005 , Description: Test Remove Button ##

  1. Pre-requisite: Steps sequence must be recorded
  1. Click on command view tab
  1. Select a recorded command row
  1. Click on Remove button

Expected result: The selected value must be deleted from the sequence steps, all other rows must not be removed.

## Test Case ID = TIDE\_I\_006 , Description: Test Insert Before Button ##

  1. Pre-requisite: Steps sequence must be recorded
  1. Click on command view tab
  1. Select a recorded command row
  1. Click on InsertBefore button
  1. Edit the Name, Target and Value text fields
  1. Click on update button
  1. Select a recorded command row
  1. Edit the Name, Target and Value text fields
  1. Click on InsertBefore button

Expected result: The edited step must be added 1 step before current selected row.


## Test Case ID = TIDE\_I\_007 , Description: Test Insert After Button ##


  1. Pre-requisite: Steps sequence must be recorded
  1. Click on command view tab
  1. Select a recorded command row
  1. Click on InsertAfter button
  1. Edit the Name, Target and Value text fields
  1. Click on update button
  1. Select a recorded command row
  1. Edit the Name, Target and Value text fields
  1. Click on InsertAfter button

Expected result: The edited step must be added 1 step after current selected row.

## Test Case ID = TIDE\_I\_008 , Description: Test Command List Columns ##

  1. Pre-requisite: Steps sequence must be recorded
  1. Click on command view tab
  1. Verify command list columns are displayed: Name, Target, Value, Variable and Status
  1. Select a recorded command row
  1. Verify Name, Target and Value columns data
  1. Click on run button
  1. Verify Status column

Expected result: All columns populate data without errors, Status column populates Pass or Fail values after the test is ran.

## Test Case ID = TIDE\_I\_009 , Description: Test Log Pane ##

  1. Pre-requisite: Steps sequence must be recorded
  1. Click on command view tab
  1. Click on run button
  1. Verify Test Log Pane

Expected result: Log pane must display errors, warnings and debug information .


## Test Case ID = TIDE\_I\_0010 , Description: Test Step into a recorded Test ##

## Test Case ID = TIDE\_I\_0011 , Description: Customize a test ##

## Test Case ID = TIDE\_I\_0012 , Description: Test Record View Tab ##

## Test Case ID = TIDE\_I\_0013 , Description: Test Command View Tab ##

## Test Case ID = TIDE\_I\_0014 , Description: Test Source View Tab ##

## Test Case ID = TIDE\_I\_0015 , Description: Test Export Groovy DSL ##

## Test Case ID = TIDE\_I\_0016 , Description: Build Tellurium IDE from Source ##

## Test Case ID = TIDE\_I\_0017 , Description: Install Tellurium IDE on Firefox/Mac ##

## Test Case ID = TIDE\_I\_0018 , Description: Install Tellurium IDE on Firefox/Ubuntu ##

## Test Case ID = TIDE\_I\_0019 , Description: Install Tellurium IDE on IE/WindowsXP ##