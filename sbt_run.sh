tbench="Null"
verilog="Null"
null="Null"

while getopts t:v: flag
do
	case "${flag}" in
		t) tbench=${OPTARG};;
		v) verilog=${OPTARG};;
	esac
done
echo " Jessica: Tbench -- $tbench";
echo " Jessica: Generate_Verilog: $verilog";
echo " Jessica: ============================================ ";

if [[ $tbench != $null ]]; then
	echo " Jessica: Running testbench: sbt \"testOnly $tbench\"... "
	sbt "testOnly $tbench"
else
	echo " Jessica: No testbench is executed"
fi

if [[ $verilog != $null ]]; then
	echo " Jessica: Generating verilog for $verilog... "
	sbt " runMain $verilog --target-dir generated/$verilog"
	echo " Jessica: verilog is generated in ./generated/$verilog"
else
	echo " Jessica: No verilog is generated"
fi
