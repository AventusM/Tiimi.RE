Feature: User can delete bookmarks.

  Scenario: book deletion is successful
    Given books are selected
    When author "Akira Toriyama" and book name "Poistettavakirja" and ISBN "978-4-08-880867-3" are submitted
    And book "Poistettavakirja" is selected
    And book is deleted
    Then book by name "Poistettavakirja" is not listed anymore

  Scenario: video deletion is succesful
    Given videos are selected
    When title "Poistettava kissavideo" and video url "https://www.youtube.com/watch?v=cbP2N1BQdYc" are submitted
    And video "Poistettava kissavideo" is selected
    And video is removed
    Then video by name "Poistettava kissavideo" is listed no more
