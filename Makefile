.PHONY: all unzip rename run-marking trim merge comments clean

all: unzip rename run-marking trim merge 

unzip: 
	bash automated_unzip.sh

rename: 
	bash rename_and_move.sh

run-marking: 
	cd marking/src/; \
	bash run_marking.sh;

trim: 
	cp trim_feedback.sh marking/results/; \
	cd marking/results/; \
	bash trim_feedback.sh;

merge:
	cd marking/results/; \
	awk '(FNR > 1)' *.csv > all_marks.csv

comments:
	mkdir final_marks;\
	xlsx2csv marks.xlsx -d "$$" > marks.csv;\
	bash create_comments.sh;

remove-zip:
	- rm -f *.zip

remove-rar:
	- rm -f *.rar

remove-res:
	- rm -f -r marking/results

remove-files:
	- rm -f *.pdf
	- rm -f *.docx
	- bash clean.sh

remove-finals:
	rm -f -r final_marks

clean: remove-files remove-zip remove-rar remove-res 
