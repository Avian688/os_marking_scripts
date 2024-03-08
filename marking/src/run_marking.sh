timeout=180 # Maximux time (in seconds) for the code to return output. Added to stop infinite loops

find . -name '*.out' -type f -exec rm -f {} \; 
find . -name '*.in' -type f -exec rm -f {} \; 
find . -name '*.txt' -type f -exec rm -f {} \; 
find . -name '*.csv' -type f -exec rm -f {} \; 
mkdir -p ../results;
for i in `ls ../../ -F | grep \/$`;
    do 
    if [[ "${i}" != "marking/" ]]; then
        timeout ${timeout}s java -cp .:../lib/junit-4.12.jar:../lib/hamcrest-all-1.3.jar MarkRunner  "../../${i}os-coursework1"  ${i%?}
    fi
done
