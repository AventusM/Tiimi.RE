Feature: user can search for read / unread books or videos

  Scenario: user searches for read books
    Given books are selected
    When author "Akira Toriyama" and book name "Toribot is back!" and ISBN "978-4-08-880867-3" are submitted with tags "anime, fighting"
    And book by name "Toribot is back!" is selected
    And the book has been marked read on its own page
    Then viewing all read books shows that the book by name "Toribot is back!" has been read

  Scenario: user searches for an unread book
    Given books are selected
    When author "Akira Toriyama" and book name "Toriyamas secret!" and ISBN "978-4-08-880867-3" are submitted
    Then viewing all unread books shows that the book by name "Toriyamas secret!" has not been read

  Scenario: user searches for a seen video
    Given videos are selected
    When title "Its less than 9000!" and video url "https://www.youtube.com/watch?v=XyNlqQId-nk" are submitted
    And video by title "Its less than 9000!" is selected
    And the video has been set to viewed on its own page
    Then viewing all viewed videos shows that the video by name "Its less than 9000!" has been viewed
