#Requires -version 3.0

$erroractionpreference = 'stop' # Quit if anything goes wrong

### Variables
$appdatadir = $env:localappdata.tolower()
$tailordir = "$appdatadir\tailor"
$tailorbindir = ";$tailordir\bin"

### Messages
function abort($msg) { write-host $msg -f darkred; exit 1 }
function warn($msg) { write-host $msg -f darkyellow; }
function success($msg) { write-host $msg -f darkgreen }

### Utilities
function remove-path() {
  $oldPath = [environment]::GetEnvironmentVariable('PATH', 'User');
  $newPath = $oldPath.Replace($tailorbindir, $NULL)
  [Environment]::SetEnvironmentVariable('Path', $newPath, [System.EnvironmentVariableTarget]::User)
}

### Ask user if they want to proceed
$response = read-host 'Press [y/Y] to continue'

if (($response -ne 'y') -or ($response -ne 'Y')) {
  abort 'Tailor uninstallation cancelled.'
}

echo ''
echo ''

### Delete Tailor directory
if (test-path $tailordir) {
  remove-item $tailordir -recurse
}

### Remove Tailor from path
remove-path $tailorbindir

success 'Tailor uninstalled.'
