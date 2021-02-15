Feature: the login

  Scenario Outline: user log in to Wamly
    #Given user navigates to wamly
    Then User log in with username "<Username>" and password "<Password>">
    Examples:
      | Username       | Password  |
      | nsovo@commandquality.co.za| Nsovo@123 |