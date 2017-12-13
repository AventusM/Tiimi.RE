Feature: user can add and browse (book)marks

  Scenario: user can add a book
    Given books are selected
    When author "Akira Toriyama" and book name "Zeno sama appears!" and ISBN "978-4-08-880867-3" are submitted
    Then book named "Zeno sama appears!" has been added

  Scenario: user can add a video
    Given videos are selected
    When title "Funniest cats from 2000!" and video url "goo.gl/WIEH6J" are submitted
    Then video named "Funniest cats from 2000!" has been added
