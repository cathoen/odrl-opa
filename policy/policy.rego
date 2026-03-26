package auth

default allow = false

deny if {
  some p in data.odrl.prohibition
  p.assignee == input.role
  p.action == input.action
  p.target == input.resource
}

allow if {
  not deny
  some p in data.odrl.permission
  p.assignee == input.role
  p.action == input.action
  p.target == input.resource
}