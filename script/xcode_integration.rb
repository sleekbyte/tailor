#!/usr/bin/env ruby
require 'xcodeproj'

# verify input
begin
  project = Xcodeproj::Project.open(ARGV[0])
rescue
  abort("Integration Error: Invalid .xcodeproj file")
end

# add build phase shell script
main_target = project.targets.first
phase = main_target.new_shell_script_build_phase("Tailor")
phase.shell_script = "/usr/local/bin/tailor"
project.save()
