# Acceptance Test Plan

## Acceptance Criteria

| Requirement | Acceptance Criteria |
|-------------------------|--------------------|
| RE-01 | Easily navigateable UI. |
| RE-02 | Ability to write notes, edit and delete existing ones. |
| RE-03 | Saving notes to a local database. |
| RE-04 | Localization in Finnish, English, Russian and Japanese. |
| RE-05 | Ability to organize notes in categories. |
| RE-06 | Database sets itself up. |
| RE-07 | Can import images to notes. |
| RE-08 | Application does not crash. |
| RE-09 | Localization changes are applied correctly every time. |


---

## Coverage Matrix

| Test ID | Requirement | Test Type |
|----------|----------------------|------------|
| AT-01 | RE-01 | Usability |
| AT-02 | RE-02 | Functional |
| AT-03 | RE-03 | Functional |
| AT-04 | RE-04 | Functional |
| AT-05 | RE-05 | Functional |
| AT-06 | RE-06 | Functional |
| AT-07 | RE-07 | Functional |
| AT-08 | RE-08 | Performance & Reliability |
| AT-09 | RE-09 | Performance & Reliability |

---

## Usability Test Cases

### AT-01

**Requirement:** RE-01

**Description:** This test is intended to determine if the application UI is easily understandable and navigateable 

**Steps:**
1. Create a workspace
2. Create a category in workspace
3. Create a note in category
4. Change localization setting

**Expected Outcome:**
A workspace is created, a category is created inside workspace and note is created inside category.
Localization is changed.

---

## Functional Test Cases

### AT-02

**Requirement:** RE-02

**Description:** This test is intended to confirm that it is possible to write, edit and delete notes.

**Steps:**
1. Create a note
2. Write inside note
3. Edit the existing note
4. Delete the existing note

**Expected Outcome:**
A note is created, text is written inside it, the text is edited and finally the note is deleted.

---

### AT-03

**Requirement:** RE-03

**Description:** This test is intended to confirm that notes are saved in a local database.

**Steps:**
1. Create a note
2. Application is closed and relaunched to confirm note and it's category and workspace persisted through relaunching

**Expected Outcome:**
Note persists through relaunch of the application and is saved in the local database.

---

### AT-04

**Requirement:** RE-04

**Description:** This test is intended to confirm that localization for the specified languages exists.

**Steps:**
1. Open settings menu
2. Confirm that options for the specified languages can be found in the settings menu
3. Cycle through available language options and confirm that changes are applied in the application after choosing a localization setting

**Expected Outcome:**
Language localization changes to the one chosen in the settings menu each time.

---

### AT-05

**Requirement:** RE-05

**Description:** This test is intended to confirm that notes can be organized in categories.

**Steps:**
1. Create a workspace
2. Create several (2-4) categories inside workspace
3. Create notes inside categories

**Expected Outcome:**
Notes are saved under the correct categories.

---

### AT-06

**Requirement:** RE-06

**Description:** This test is intended to confirm that an empty database is created on launcing the application if it didn't already exist.

**Steps:**
1. Launch application without a database
2. Confirm that database exists

**Expected Outcome:**
Database created.

---

### AT-07

**Requirement:** RE-07

**Description:** This test is intended to confirm that images can be imported to notes.

**Steps:**
1. Create note
2. Import image to note using image button in note interface

**Expected Outcome:**
Image thumbnail added to note.

---

## Performance & Reliability Test Cases

### AT-08

**Requirement:** RE-08

**Description:** This test is intended to confirm that the application does not crash during normal use.

**Steps:**
1. Launch application
2. Use all app features such as creating notes, workspaces and categories, changing localisation and uploading images.

**Expected Outcome:**
Application does not crash.

---

### AT-09

**Requirement:** RE-09

**Description:** This test is intended to confirm that changing the localisation setting always changes it correctly without any missing symbols or defective formatting.

**Steps:**
1. Go through all localisation options (English, Finnish, Russian, Japanese) one by one
2. After changing to each one check the applicaton interface for any inconsistencies

**Expected Outcome:**
Localisation changes apply correctly.

---
