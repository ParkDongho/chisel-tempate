#!/bin/sh
CURR_DIR=`pwd -P`
cd /tools/Xilinx/Vivado/2022.1
source ./settings64.sh
cd $CURR_DIR

while getopts p: opts; do
  case $opts in
    p) PROJ_NAME=$OPTARG
      ;;
  esac
done

#vivado ../../xilinx/$PROJ_NAME/$PROJ_NAME.xpr
cd ../../xilinx/project
vivado -source ../source/$PROJ_NAME.tcl
vivado -mode tcl -source ../source/update_project.tcl ./$PROJ_NAME/$PROJ_NAME.xpr
cd $CURR_DIR

rm *jou
rm *log
rm *zip
