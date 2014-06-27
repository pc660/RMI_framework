rm -r test
mkdir test
cp -r src/ test/src
cp build.xml test/build.xml
cp Makefile test/Makefile
cd test
make
rm bin/HelloWorld/HelloWorld_Interface_stub.class 
cd ..
java -cp bin: Server.Main registry &
sleep 1
java -cp bin: Server.Main server &
cd test
sleep 1
java -cp bin: Server.Main client &