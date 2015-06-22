#!/usr/bin/env sh
# Usage: script/install-antlr.sh
# Installs ANTLR and runs script/antlr.

set -e

# Download ANTLR into current directory
wget http://antlr.org/download/antlr-4.5-complete.jar

# Execute ANTLR Tool
script/antlr
