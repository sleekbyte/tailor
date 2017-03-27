#Requires -version 3.0

$erroractionpreference = 'stop' # Quit if anything goes wrong

### Variables
$appdatadir = $env:localappdata.tolower()
$tailorversion = '0.12.0'
$tailor = 'tailor'
$tailorzip = "$tailor-$tailorversion.zip"
$tailorinstalldir = "$appdatadir\$tailor"
$tailorbindir = "$tailorinstalldir\$tailor-$tailorversion\bin"
$zipurl = "https://github.com/sleekbyte/tailor/releases/download/v$tailorversion/$tailorzip"
$zipfile = "$env:temp\$tailorzip"
$javaversion='1.8'

### Messages
function abort($msg) { write-host $msg -f darkred; exit 1 }
function warn($msg) { write-host $msg -f darkyellow; }
function success($msg) { write-host $msg -f darkgreen }

### Utilities
function verify-java() {
  $output = dir 'HKLM:\SOFTWARE\JavaSoft\Java Runtime Environment'  | select -expa pschildname -Last 1
  if ($output -eq $NULL -or $output -notmatch $javaversion) {
    abort "Tailor requires Java version $javaversion to be installed and JAVA_HOME to be set correctly.
Install Java version $javaversion and/or set JAVA_HOME then try again after verifying with: java -version"
  }
}

function dl($url,$to) {
  Invoke-WebRequest $url -OutFile $to
}

function unzip($file, $destination) {
  if(!(test-path $file)) { abort "can't find $file to unzip"}
  $zipPackage = (new-object -com shell.application).NameSpace($file)
  $destinationFolder = (new-object -com shell.application).NameSpace($destination)
  $destinationFolder.CopyHere($zipPackage.Items(),0x14)
}

function configure-path() {
  $oldPath = [environment]::GetEnvironmentVariable("PATH", "User");
  if ($oldPath -match [regex]::Escape($tailorbindir)) { return }
  $env:path = "$env:path;$tailorbindir"
  $newPath = "$oldPath;$tailorbindir" # Ensure tailor runs in current PowerShell instance
  [Environment]::SetEnvironmentVariable('Path', $newPath, [System.EnvironmentVariableTarget]::User)
}

### Ask user if they want to proceed
$response = read-host 'Press [y/N] to continue'

if ($response -ne 'y' -or $response -ne 'Y') {
  abort 'Tailor installation cancelled.'
}

echo ''
echo ''

### Verify Java installation
verify-java

### Install tailor
echo "Downloading $tailor.zip to $appdatadir"
dl $zipurl $zipfile

new-item -ItemType Directory -Path $tailorinstalldir
unzip $zipfile $tailorinstalldir
remove-item $zipfile

configure-path

success 'Ready to Tailor your Swift!'
