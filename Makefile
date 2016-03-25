#JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        flatscreator/FlatSwing.java \
        flatscreator/Flat.java \
        flatscreator/Sheet.java \

default: jar

jar: classes
	jar cfm flatscreator.jar Manifest.txt flatscreator/ com/

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *~
	$(RM) flatscreator/*~

distclean: clean
	$(RM) flatscreator/*.class
	$(RM) *jar