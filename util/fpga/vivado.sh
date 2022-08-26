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
PROJ_DIR="$CURR_DIR/../../xilinx/project/$PROJ_NAME"
echo $PROJ_DIR
cd ../../xilinx/project
if [ -d "$PROJ_DIR" ]; then
  echo "open vivado project : $PROJ_NAME" && \
    vivado ./$PROJ_NAME/$PROJ_NAME.xpr && \
    vivado -mode tcl -source ../source/update_project.tcl ./$PROJ_NAME/$PROJ_NAME.xpr
else
  echo "generate vivado project : $PROJ_NAME" && \
  vivado -source ../source/$PROJ_NAME.tcl && \
  echo "update tcl script : $PROJ_NAME" && \
  vivado -mode tcl -source ../source/update_project.tcl ./$PROJ_NAME/$PROJ_NAME.xpr
fi

rm *jou
rm *log
rm *zip
rm *str

cd $CURR_DIR
