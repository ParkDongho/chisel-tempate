# !/bin/sh

CURR_DIR=`pwd -P`
echo "$CURR_DIR"

source ~/.bashrc
source "$VIVADO_DIR/settings64.sh"
cd $CURR_DIR

#cd ../../
PROJ_DIR=`pwd -P`

cd $PROJ_DIR
PROJ_NAME="${PWD##*/}"
echo $PROJ_NAME


while getopts p: opts; do
  case $opts in
    p) PROJ_NAME=$OPTARG
      ;;
  esac
done

VIVADO_PROJ_DIR="$PROJ_DIR/fpga/project/$PROJ_NAME"

function dispPATH(){
  echo "PROJECT DIRECTORY : $PROJ_DIR"
  echo "VIVADO PROJECT DIRECTRORY : $VIVADO_PROJ_DIR"
  echo "VIVADO DIRECTORY  : $VIVADO_DIR"
}

dispPATH

cd "$PROJ_DIR/fpga/project"
# vivado open
if [ -d "$VIVADO_PROJ_DIR" ]; then # open vivado project using .xpr
  pwd
  echo "opening vivado project... : $PROJ_NAME" && \
  vivado ./$PROJ_NAME/$PROJ_NAME.xpr && \
  vivado -mode batch -source "$PROJ_DIR/scripts/fpga/update_vivado_project.tcl" "$VIVADO_PROJ_DIR/$PROJ_NAME.xpr" -tclargs $PROJ_NAME 
elif [ -f "$PROJ_DIR/fpga/source/$PROJ_NAME.tcl" ]; then # project recreation using a .tcl script
  pwd
  echo "regenerating vivado project from .tcl... : $PROJ_NAME" && \
  vivado -source $PROJ_DIR/fpga/source/$PROJ_NAME.tcl && \
  echo "updating tcl script... : $PROJ_NAME" && \
  vivado -mode batch -source "$PROJ_DIR/scripts/fpga/update_vivado_project.tcl" "$VIVADO_PROJ_DIR/$PROJ_NAME.xpr" -tclargs $PROJ_NAME 
else # create new vivado project
  echo "no vivado project... : $PROJ_NAME" && \
  vivado && \
  echo "updating tcl script... : $PROJ_NAME" && \
  vivado -mode batch -source "$PROJ_DIR/scripts/fpga/update_vivado_project.tcl" "$VIVADO_PROJ_DIR/$PROJ_NAME.xpr" -tclargs $PROJ_NAME 
fi

rm *jou
rm *log
rm *zip
rm *str

cd $CURR_DIR


