#!/usr/bin/env sh

set -e

black='\033[0;30m'
red='\033[1;31m'
green='\033[1;32m'
blue='\033[1;34m'
reset='\033[0m'

PREFIX="/usr/local"
TAILOR_DIR="$PREFIX/tailor"
BIN_DIR="$PREFIX/bin"
MAN_DIR="$PREFIX/share/man/man1"

wait_for_user() {
  printf "Enter [y/N] to continue: "
  read -r CONTINUE < /dev/tty
  echo; echo
  [ "$CONTINUE" = "y" ] || [ "$CONTINUE" = "Y" ]
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

echo "Tailor will be uninstalled from: $blue$TAILOR_DIR/$reset"
if wait_for_user; then
  maybe_sudo /bin/rm -rf "$TAILOR_DIR"
  maybe_sudo /bin/rm -f "$BIN_DIR"/tailor
  maybe_sudo /bin/rm -f "$MAN_DIR"/tailor.1
  kill_sudo

  cecho "Tailor uninstalled." "$green"
else
  cecho "Tailor uninstallation cancelled." "$red"
fi
