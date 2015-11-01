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
START_SCRIPT="$TAILOR_DIR/bin/tailor"
TAILOR_ZIP_URL="https://github.com/sleekbyte/tailor/releases/download/v0.2.1/tailor.zip"
JAVA_VERSION="1.8"

wait_for_user() {
  if [ $(uname) = "Darwin" ]; then
    read -n 1 -a CONTINUE -p "Press [y/N] to continue: " < /dev/tty
  elif [ $(uname) = "Linux" ]; then
    echo -n "Press [y/N] to continue: "
    read CONTINUE < /dev/tty
  fi
  echo; echo
  [ "$CONTINUE" = "y" ] || [ "$CONTINUE" = "Y" ]
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

echo "Tailor will be installed to: $blue$TAILOR_DIR/$reset"
if wait_for_user; then
  verify_java
  maybe_sudo /bin/mkdir -p $BIN_DIR
  cecho "Downloading tailor.zip to $PREFIX/..." $blue
  maybe_sudo /usr/bin/curl -#fLo "$PREFIX"/tailor.zip "$TAILOR_ZIP_URL"
  maybe_sudo /usr/bin/unzip -oqq "$PREFIX"/tailor.zip -d "$PREFIX"
  maybe_sudo /bin/rm -rf "$PREFIX"/tailor.zip
  maybe_sudo /bin/ln -fs "$START_SCRIPT" "$BIN_DIR"/tailor
  if [ $(uname) = "Darwin" ]; then
    maybe_sudo /usr/sbin/chown -R $(/usr/bin/whoami) "$TAILOR_DIR"
  elif [ $(uname) = "Linux" ]; then
    maybe_sudo /bin/chown -R $(/usr/bin/whoami) "$TAILOR_DIR"
  fi
  kill_sudo

  cecho "Ready to Tailor your Swift!" $green
else
  cecho "Tailor installation cancelled." $red
fi
