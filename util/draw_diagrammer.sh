#!/bin/sh
CURR_DIR=`pwd -P`

while getopts f: opts; do
  case $opts in
    f) FILE_NAME=$OPTARG
      ;;
  esac
done

# Change Java Version 
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
source ~/.bash_profile

# Run Diagrammer
cd ~/Library/diagrammer
./diagram.sh -i $CURR_DIR/generated/"$FILE_NAME".fir

# Change Java Version
export JAVA_HOME=$(/usr/libexec/java_home -v 18)
source ~/.bash_profile

cd $CURR_DIR
