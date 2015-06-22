#!/usr/bin/env sh

set -ex

cd /usr/local/lib
wget http://antlr.org/download/antlr-4.5-complete.jar
export CLASSPATH=".:/usr/local/lib/antlr-4.5-complete.jar:$CLASSPATH"
