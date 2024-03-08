
find . -name "* *" -type d | rename 's/ //g';
find . -name "* *" -type f | rename 's/ //g';

for i in `ls -F | grep /`;
    do 
    find $i -name '__MACOSX' -type d -exec rm -f -r {} \; 
    find $i -name '*.pdf' -exec mv {} $i \;
    find $i -name '*.doc*' -exec mv {} $i \;
    for f in `find "$i" -type d`; do
        if [[ $(ls -1 "$f" | grep -c "src") == 1 && $(ls -1 "$f" | grep -c "experiment*") > 0 ]]; then
            cp -r $f "${i}os-coursework1";
        fi
    done
done
