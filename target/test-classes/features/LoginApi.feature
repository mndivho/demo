Feature:The API login Rest call

  Scenario Outline: User make API login Rest call to Wamly
    Given Wamly rest endpoints are available
    Then User sends username <"Username"> and password <"Password">
    Examples:
      | Username       | Password  |
      | www.google.com | vdcudjcsj |