Feature: user can add and browse (book)marks

  Scenario: user can add a book
    Given books are selected
    When author "Akira Toriyama" and book name "MOIMOI" and ISBN "978-4-08-880867-3" are submitted
    Then book named "MOIMOI" has been added

  Scenario: user can add a video
    Given videos are selected
    When title "Cat video #1" and video url "goo.gl/WIEH6J" are submitted
    Then video named "Cat video #1" has been added
