Feature: user can search for pre-existing bookmarks

  #A book is added, then searched for through tags
  Scenario: user succesfully searches for a book through tags
    Given books are selected
    When author "Akira Toriyama" and book name "Winners of eurovision" and ISBN "978-4-08-880867-3" and tags "anime" have been submitted
    And search is selected
    And search is performed by keyword "anime"
    Then query returns book by name "Winners of eurovision"

  Scenario: user succesfully searches for a book through title
    Given books are selected
    When author "Akira Toriyama" and book name "Jeejee123123123" and ISBN "978-4-08-880867-3" are submitted with tags "anime"
    And search is selected
    And search is performed by title "Jeejee"
    Then query returns book by name "Jeejee123123123"
