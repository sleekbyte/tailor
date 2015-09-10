#!/usr/bin/env sh

set -e

black='\033[0;30m'
red='\033[1;31m'
green='\033[1;32m'
blue='\033[1;34m'
reset='\033[0m'

PREFIX="/usr/local"
TAILORDIR="$PREFIX/tailor"
BINDIR="$PREFIX/bin"
STARTSCRIPT="$TAILORDIR/bin/tailor"

wait_for_user() {
  read -n 1 -a CONTINUE -p "Press [y/N] to continue: " < /dev/tty
  echo; echo
  [ "$CONTINUE" == "y" ] || [ "$CONTINUE" == "Y" ]
}

maybe_sudo() {
  if [ -w "$PREFIX" ]; then
    "$@"
  else
    /usr/bin/sudo "$@"
  fi
}

kill_sudo() {
  if [ ! -w "$PREFIX" ]; then
    /usr/bin/sudo -k
  fi
}

cecho() {
  message=$1
  color=${2:-$black}
  echo "$color$message$reset"
}

echo "Tailor will be uninstalled from: $blue$TAILORDIR/$reset"
if wait_for_user; then
  maybe_sudo /bin/rm -rf "$TAILORDIR"
  maybe_sudo /bin/rm -f "$BINDIR"/tailor
  kill_sudo

  cecho "Tailor uninstalled." $green
else
  cecho "Tailor uninstallation cancelled." $red
fi
