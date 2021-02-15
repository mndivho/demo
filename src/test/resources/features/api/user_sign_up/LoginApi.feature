Feature:The API login Rest call

  Scenario Outline: User make API login Rest call to Wamly
    Given Wamly rest endpoints are available
    Then User sends via backend username <"Username"> and password <"Password">
    Examples:
      | Username       | Password  |
      | www.google.com | vdcudjcsj |