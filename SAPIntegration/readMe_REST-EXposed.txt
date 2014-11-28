WARNING! WARNING! This is not the API. The real REST API is 
defined in the API subdirectory, in RAML format.


GET {timesheet-server}/timesheet/workingDay?userCode=XX&userCode=010113
 produces(JSON EXAMPLE)
  {"dayInMonth":1,"status":1,"loggedWorkItems":[{"hours":8,"network":{"code":"XXX3700zzz","name":"VIX"},"activity":{"code":"act1","name":"Activity 1"}}]}

GET {timesheet-server}/timesheet/listAllNetworks
 produces(JSON EXAMPLE)
  {"items":[{"code":"XX","name":"XX"},{"code":"XX","name":"XX"},{"code":"XX","name":"XX"}]}



GET {timesheet-server}/timesheet/network?code=XX
 produces(JSON EXAMPLE)
  {"code":"XX","name":"XX"}


GET {timesheet-server}/timesheet/networkActivities?code=XX
 produces(JSON EXAMPLE)
  {"items":[{"code":"XX","name":"XX"},{"code":"XX","name":"XX"},{"code":"XX","name":"XX"}]}


GET {timesheet-server}/timesheet/workingMonth?userCode=XX&monthYear=0113
 produces(JSON EXAMPLE)
  {"userCode":"XX","workingDays":[{"dayInMonth":1,"status":1},{"dayInMonth":2,"status":2}]}