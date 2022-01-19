tbench="Null"
verilog="Null"
null="Null"

# Input flags
while getopts t:v: flag
do
	case "${flag}" in
		t) tbench=${OPTARG};;
		v) verilog=${OPTARG};;
	esac
done

echo ">>Jessic:   Tbench: $tbench";
echo ">>Jessic:   Generate_Verilog: $verilog";
echo ">>Jessic:   ============================================ ";

# Create output directories
if [ ! -d "generated" ]; then
	mkdir generated
fi

if [ ! -d "generated/tb" ]; then
	mkdir generated/tb
fi

if [ -d "generated/tb/$tbench" ]; then
	rm -rf generated/tb/$tbench
fi
mkdir generated/tb/$tbench


# Run Sbt
if [[ $tbench != $null ]]; then
	echo ">>Jessic:   Running testbench: sbt \"testOnly $tbench -- -DvwriteVcd=1 \"... "
	sbt "testOnly $tbench -- -DvwriteVcd=1"

	mv ./test_run_dir ./generated/tb/$tbench
	echo ">>Jessic:   waveform is generated in ./generated/tb/$tbench"
else
	echo ">>Jessic:   No testbench is executed"
fi

if [[ $verilog != $null ]]; then
	echo ">>Jessic:   Generating verilog for $verilog... "
	sbt " runMain $verilog --target-dir generated/verilog/$verilog"
	echo ">>Jessic:   verilog is generated in ./generated/verilog/$verilog"
else
	echo ">>Jessic:   No verilog is generated"
fi
