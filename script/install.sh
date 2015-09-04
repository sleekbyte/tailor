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
  echo "\n"
  [ "$CONTINUE" == "y" ]
}

cecho() {
  message=$1
  color=${2:-$black}
  echo "$color$message$reset"
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

echo "Tailor will be installed to: $blue$TAILORDIR/$reset"
if wait_for_user; then
  maybe_sudo /bin/mkdir -p $BINDIR
  maybe_sudo /usr/bin/curl -fsSLo "$PREFIX"/tailor.zip https://github.com/sleekbyte/tailor/releases/download/0.1.0/tailor.zip
  maybe_sudo /usr/bin/unzip -oqq "$PREFIX"/tailor.zip -d "$PREFIX"
  maybe_sudo /bin/rm -rf "$PREFIX"/tailor.zip
  maybe_sudo /bin/ln -fs "$STARTSCRIPT" "$BINDIR"/tailor
  maybe_sudo /bin/chmod -R g+rwx "$TAILORDIR"
  maybe_sudo /usr/bin/chgrp -R "admin" "$TAILORDIR"
  kill_sudo

  cecho "Tailor is ready to rock!!1" $green
else
  cecho "Tailor set up cancelled." $red
fi
