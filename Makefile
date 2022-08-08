SBT = sbt
SOURCE = source
OPEN = open
CURR_DIR:=$(shell pwd)

SSH_FILE := ~/dev/chisel/setup/ssh.txt 
PORT_FILE := ~/dev/chisel/setup/port.txt
REMOTE_SSH := $(shell cat ${SSH_FILE})
REMOTE_PORT := $(shell cat ${PORT_FILE})

# 서버상의 Vivado 프로젝트의 verilog 파일이 저장되는 디렉토리를 설정
REMOTE_PROJ_DIR := ~/Documents/dev/NPUgen-chisel-project/NPUgen-chisel-project.srcs/sources_1/new

#######################
# Generate Verilog Code
Add:
	$(SBT) "runMain empty.ALUMain" \
		&& scp -P ${REMOTE_PORT} ./generated/ALU.v \
		${REMOTE_SSH}:${REMOTE_PROJ_DIR}

##############
# Run the test
Add_Test:
	$(SBT) "testOnly AddTest"

##################################
# Draw Timing Diagram with GTKWave
Add_Wave:
	$(OPEN) ./test_run_dir/Add_should_pass/*.vcd

####################
# Draw Block Diagram
Add_Diagrammer:
	$(SOURCE) ./util/draw_diagrammer.sh -f "Add"

#########
# Clean
Clean:
	rm -rf generated
	rm -rf project
	rm -rf target

########
# Drawio
Drawio:
	cd util && $(SOURCE) ./drawio.sh
DrawioNew:
	@read -p "Enter File Name:" file_name; \
	cp ./blank.drawio ./diagram/$$file_name.drawio \
	&& open ./diagram/$$file_name.drawio
DrawioExport:
	/Applications/draw.io.app/Contents/MacOS/draw.io \
		-x -f svg -o target/scala-2.12/api/empty src/main/doc-resources/empty

########
# Wavedrom
Wavedrom:
	cd ./util \
	&& $(SOURCE) ./wavedrom.sh

#################
# ScalaDoc
Doc:
	make Drawio \
		&& make DocExport
DocExport:
	$(SBT) doc
DocOpen:
	open ./target/scala-2.12/api/index.html
help:
	echo "사용방법"
	echo ""
