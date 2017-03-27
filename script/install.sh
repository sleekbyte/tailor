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
TAILOR_VERSION="0.12.0"
TAILOR_ZIP="tailor-$TAILOR_VERSION.zip"
START_SCRIPT="$TAILOR_DIR/tailor-$TAILOR_VERSION/bin/tailor"
MAN_PAGE="$TAILOR_DIR/tailor-$TAILOR_VERSION/tailor.1"
TAILOR_ZIP_URL="https://github.com/sleekbyte/tailor/releases/download/v$TAILOR_VERSION/$TAILOR_ZIP"
JAVA_VERSION="1.8"
UNAME=$(uname)

wait_for_user() {
  printf "Enter [y/N] to continue: "
  read -r CONTINUE < /dev/tty
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
    err "Tailor requires" "$blue""Java version $JAVA_VERSION""$reset" "to be installed and" "$blue""JAVA_HOME""$reset" "to be set correctly."
    err "$red""Install Java version $JAVA_VERSION""$reset" "and/or" "$red""set JAVA_HOME""$reset"", then try again after verifying with:"
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

echo "Tailor $TAILOR_VERSION will be installed to: $blue$TAILOR_DIR/$reset"
if wait_for_user; then
  verify_java
  maybe_sudo /bin/mkdir -p $BIN_DIR $TAILOR_DIR $MAN_DIR
  cecho "Downloading $TAILOR_ZIP to $TAILOR_DIR/..." "$blue"
  maybe_sudo /usr/bin/curl -#fLo "$TAILOR_DIR/$TAILOR_ZIP" "$TAILOR_ZIP_URL"
  maybe_sudo /usr/bin/unzip -oqq "$TAILOR_DIR/$TAILOR_ZIP" -d "$TAILOR_DIR"
  maybe_sudo /bin/rm -rf "$TAILOR_DIR/$TAILOR_ZIP"
  maybe_sudo /bin/ln -fs "$START_SCRIPT" "$BIN_DIR"/tailor
  maybe_sudo /bin/ln -fs "$MAN_PAGE" "$MAN_DIR"/tailor.1
  if [ "$UNAME" = "Darwin" ]; then
    maybe_sudo /usr/sbin/chown -R "$(/usr/bin/whoami)" "$TAILOR_DIR"
  elif [ "$UNAME" = "Linux" ]; then
    maybe_sudo /bin/chown -R "$(/usr/bin/whoami)" "$TAILOR_DIR"
  fi
  kill_sudo

  cecho "Ready to Tailor your Swift!" "$green"
else
  cecho "Tailor installation cancelled." "$red"
fi
