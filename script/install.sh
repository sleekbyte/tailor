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
TAILORZIPURL="https://github.com/sleekbyte/tailor/releases/download/v0.1.0/tailor.zip"
JAVA_VERSION="1.8"

wait_for_user() {
  read -n 1 -a CONTINUE -p "Press [y/N] to continue: " < /dev/tty
  echo; echo
  [ "$CONTINUE" == "y" ] || [ "$CONTINUE" == "Y" ]
}

err() {
  echo >&2 "$@"
}

verify_java() {
  set +e
  _java=$(java -version 2>&1)
  _java_ret=$?
  set -e
  if [ "$_java_ret" -ne 0 ] || [ -n "${_java##*$JAVA_VERSION*}" ]; then
    err "Tailor requires" $blue"Java version $JAVA_VERSION"$reset "to be installed and" $blue"JAVA_HOME"$reset "to be set correctly."
    err $red"Install Java version $JAVA_VERSION"$reset "and/or" $red"set JAVA_HOME"$reset", then try again after verifying with:"
    err "$green    java -version$reset"
    err
    err "JAVA_HOME=$JAVA_HOME"
    err
    exit 1
  fi
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

echo "Tailor will be installed to: $blue$TAILORDIR/$reset"
if wait_for_user; then
  verify_java
  maybe_sudo /bin/mkdir -p $BINDIR
  cecho "Downloading tailor.zip to $PREFIX/..." $blue
  maybe_sudo /usr/bin/curl -#fLo "$PREFIX"/tailor.zip "$TAILORZIPURL"
  maybe_sudo /usr/bin/unzip -oqq "$PREFIX"/tailor.zip -d "$PREFIX"
  maybe_sudo /bin/rm -rf "$PREFIX"/tailor.zip
  maybe_sudo /bin/ln -fs "$STARTSCRIPT" "$BINDIR"/tailor
  maybe_sudo /usr/sbin/chown -R $(/usr/bin/whoami) "$TAILORDIR"
  kill_sudo

  cecho "Ready to Tailor your Swift!" $green
else
  cecho "Tailor installation cancelled." $red
fi
