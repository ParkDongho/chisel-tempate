SBT      = sbt
SOURCE   = source
OPEN     = open CURR_DIR = $(shell pwd)
UNAME    = $(shell uname)
VIVADO   = vivado

SSH_FILE    := ~/dev/chisel/setup/ssh.txt 
PORT_FILE   := ~/dev/chisel/setup/port.txt REMOTE_SSH  := $(shell cat ${SSH_FILE})
REMOTE_PORT := $(shell cat ${PORT_FILE})

# 서버상의 Vivado 프로젝트의 verilog 파일이 저장되는 디렉토리를 설정
REMOTE_PROJ_DIR := ~/Documents/dev/NPUgen-chisel-project/NPUgen-chisel-project.srcs/sources_1/new

#######################
# Generate Verilog Code
run:
	$(SBT) "runMain example.HelloMain" 
#		&& scp -P ${REMOTE_PORT} ./generated/Hello.v \
#		${REMOTE_SSH}:${REMOTE_PROJ_DIR}

##############
# Run the test
test:
	$(SBT) "testOnly HelloTest"

##################################
# Draw Timing Diagram with GTKWave
gtkwave:
	$(OPEN) ./test_run_dir/Add_should_pass/*.vcd

####################
# Draw Block Diagram
diagrammer:
	$(SOURCE) ./util/draw_diagrammer.sh -f "Hello"

#########
# Clean
clean:
	rm -rf generated
	rm -rf project
	rm -rf target
	cd xilinx/project && rm -rf `ls |grep -v project.md`

########
# Drawio
drawio:
	cd util \
		&& $(SOURCE) ./drawio.sh

drawioNew:
	@read -p "Enter File Name:" file_name; \
	cp ./blank.drawio ./diagram/$$file_name.drawio \
	&& open ./diagram/$$file_name.drawio

########
# Wavedrom
wavedrom:
	cd util \
		&& $(SOURCE) ./wavedrom.sh

##########
# ScalaDoc
doc:
	make docExport \
		&& make drawio \
		&& make wavedrom \
		&& cp -r -f $(CURR_DIR)/src/main/resource/drawio/ \
		  $(CURR_DIR)/target/scala-2.12/api/ \
		&& cp -r -f $(CURR_DIR)/src/main/resource/wavedrom/ \
		  $(CURR_DIR)/target/scala-2.12/api/ \
		&& find $(CURR_DIR)/src/main/resource/drawio/ \
		  -type f -name "*.svg" -exec rm {} \; \
		&& find $(CURR_DIR)/src/main/resource/wavedrom/ \
		  -type f -name "*.svg" -exec rm {} \;
docExport:
	$(SBT) doc
docOpen:
	open ./target/scala-2.12/api/index.html

#########
# Vivado
vivado:
	if [ ! -d "./generated" ];then \
		make run;                    \
	fi                             \
	&& cd ./util/fpga && ./vivado.sh -p "helloworld" &

help:
	echo "HELP"
	echo ""
