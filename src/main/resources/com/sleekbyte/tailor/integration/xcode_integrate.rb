#!/usr/bin/env ruby
require 'pathname'
# http://stackoverflow.com/a/5471032
def which(cmd)
  exts = ENV['PATHEXT'] ? ENV['PATHEXT'].split(';') : ['']
  ENV['PATH'].split(File::PATH_SEPARATOR).each do |path|
    exts.each { |ext|
      exe = File.join(path, "#{cmd}#{ext}")
      return exe if File.executable?(exe) && !File.directory?(exe)
    }
  end
  return nil
end
def find_tailor()
  return Pathname.new(File.realpath(which('tailor'))) + '../..'
end
tailor_gems_dir = find_tailor + 'gems/installed'
tailor_cache_dir = find_tailor + 'gems/vendor/cache'
ENV['GEM_HOME'] = tailor_gems_dir.to_s
ENV['GEM_PATH'] = tailor_gems_dir.to_s
cmd = "gem install --local --no-rdoc --no-ri xcodeproj-*.gem"
Dir.chdir(tailor_cache_dir.to_s){ %%x[#{cmd}] }
require 'xcodeproj'
begin
  project = Xcodeproj::Project.open("%s")
rescue
  abort("Integration Error: Invalid .xcodeproj file")
end
main_target = project.targets.first
phase = main_target.new_shell_script_build_phase("Tailor")
phase.shell_script = %%q(if hash tailor 2>/dev/null; then
  tailor
else
  echo "warning: Please install Tailor from https://tailor.sh"
fi)
project.save()
