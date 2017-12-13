Feature: user can search for read / unread books or videos

  Scenario: user searches for read books
    Given books are selected
    When author "Akira Toriyama" and book name "Best book 2017" and ISBN "978-4-08-880867-3" are submitted with tags "anime, fighting"
    And book by name "Best book 2017" is selected
    And the book has been marked read on its own page
    Then viewing all read books shows that the book by name "Best book 2017" has been read

  Scenario: user searches for an unread book
    Given books are selected
    When author "Akira Toriyama" and book name "MOIMOI" and ISBN "978-4-08-880867-3" are submitted
    Then viewing all unread books shows that the book by name "MOIMOI" has not been read

  Scenario: user searches for a seen video
    Given videos are selected
    When title "Cat video #90001" and video url "https://www.youtube.com/watch?v=XyNlqQId-nk" are submitted
    And video by title "Cat video #90001" is selected
    And the video has been set to viewed on its own page
    Then viewing all viewed videos shows that the video by name "Cat video #90001" has been viewed
