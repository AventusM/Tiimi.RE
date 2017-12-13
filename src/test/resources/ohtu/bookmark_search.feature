Feature: user can search for pre-existing bookmarks

  #A book is added, then searched for through tags
  Scenario: user succesfully searches for a book
    Given books are selected
    When author "Akira Toriyama" and book name "The Winning Universe is Decided!" and ISBN "978-4-08-880867-3" are submitted with tags "anime, fighting"
    And search is selected
    And search is performed by keyword "anime"
    Then query returns book by name "The Winning Universe is Decided!"

