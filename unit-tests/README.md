## Help to set config for all OS
```sh
https://www.tutorialspoint.com/junit/junit_environment_setup.htm
```

### Mac

## HomeBrew Install and wget Install
```sh
# Install mac homebrew, to know more read https://brew.sh/index_fr
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"

# Install wget in mac
brew install wget
```

## Junit lib & Hamcrest lib Download

```sh
cd /Library/Java/
sudo mkdir JUNIT
cd JUNIT/
sudo wget https://github.com/junit-team/junit4/releases/download/r4.12/junit-4.12.jar
sudo wget https://repo1.maven.org/maven2/org/hamcrest/hamcrest-all/1.3/hamcrest-all-1.3.jar
sudo chown $USER /Library/Java/JUNIT/*
sudo chmod 755 /Library/Java/JUNIT /Library/Java/JUNIT/*
```

## Junit & Hamcrest Configuration

```sh
vim ~/.bash_profile
# Put this line at the end of file
export CLASSPATH=/Library/Java/JUNIT/junit-4.12.jar:/Library/Java/JUNIT/hamcrest-all-1.3.jar:.
# Save file and exit VIM by doing: esc : wq!
# Refresh terminal config
source ~/.bash_profile
```

### Windows

## Install JUnit & Hamcrest
```sh
Download on Oracle the Java JDK 1.8
```
```sh
Download JUnit 4 (>= 4.12)
Download HamCrest-2.2.jar;
Download HamCrest-all-1.3.jar
```
```sh
# Do Step 1 to 5 on :
https://www.tutorialspoint.com/junit/junit_environment_setup.htm
Dont forget to create the environment variables (JAVA_HOME, JUNIT_HOME, CLASSPATH)
```
# Warning :
```sh
CLASSPATH = %CLASSPATH%;%JUNIT_HOME%\*;.;
and update PATH
```

## Clone the repository on your computer

Clone the project 
```sh
git clone https://github.com/adesousa/agile-testing.git
# OR clone a fork of the project if you want to work on a remote repository with other people: 
git clone https://github.com/###/agile-testing.git

cd agile-testing/
```

## First of all, you need to compile your src classes and test cases (from src folder):

```sh
# Important, to avoid compile errors
cd src/

### Classes
javac -d ../bin/ codingfactory/rpgconsole/enemy/*.java
javac -d ../bin/ codingfactory/rpgconsole/hero/*.java
javac -d ../bin/ codingfactory/rpgconsole/game/*.java

### Tests
javac -d ../bin/ test/HeroTest.java
```

## Play with the Java App (from bin folder)

```sh
cd bin/
java codingfactory.rpgconsole.game.Starter
```

## Then run your test cases (from bin tests folder)

```sh
cd bin/
java org.junit.runner.JUnitCore test.HeroTest
```

## Enjoy Agile Testing

Thank you.
