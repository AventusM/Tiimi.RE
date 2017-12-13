Feature: user can edit bookmarks

  Scenario: user can edit title
    Given new book has been added
    And book has been selected to be edit
    When user change title to "Uusi title"
    Then book data contains "Uusi title"

  Scenario: user can edit author
    Given new book has been added
    And book has been selected to be edit
    When user change author to "Uusi author"
    Then book data contains "Uusi author"

  Scenario: user can edit tags
    Given new book has been added
    And book has been selected to be edit
    When user change tags to "Uusi kurssi, toinen kurssi"
    Then book data contains "Uusi kurssi, toinen kurssi"

  Scenario: user can edit ISBN
    Given new book has been added
    And book has been selected to be edit
    When user change ISBN to "978-951-98548-9-2"
    Then book data contains "978-951-98548-9-2"

  Scenario: user can set that the book has been read
    Given books are selected
    When author "Akira Toriyama" and book name "Uusin animekirja" and ISBN "978-4-08-880867-3" are submitted
    And book "Uusin animekirja" is selected
    And edit button is pressed
    And book has been set to read
    Then the book has been read
