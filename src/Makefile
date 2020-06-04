JAVAC = javac
JARC = jar cvmf
ENGINE = dist/l2-pa-project-samanii.jar

jar: build pack

build: clean
	# Find all .java sources and copy their path to a file
	find ./* -type f -name *.java -print > sources.txt
	
	# Compile each .java file
	$(JAVAC) @sources.txt
	rm sources.txt

pack:
	# Create the manifest.mf file that specifies where the main method is located
	@echo "Manifest-Version: 1.0" > manifest.mf
	@echo "Main-Class: engine.Main" >> manifest.mf
	@echo "" >> manifest.mf
	
	# Same step as above, but for .class files
	mkdir dist
	find ./ -type f -name *.class -print > classes.txt
	
	# Pack .class files as a single .jar package
	$(JARC) manifest.mf $(ENGINE) @classes.txt
	rm manifest.mf
	rm classes.txt

run:
	./run.sh

clean:
	rm -rf maneifest.mf dist
	find ./ -type f -name "*.class" -delete

