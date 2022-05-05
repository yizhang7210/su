# su
塑 - A feature engineering framework

# Local set up
### Get Java 17
1. `brew install openjdk@17`
2. `sudo ln -sfn /usr/local/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk`
3. `brew install jenv`
4. `jenv add /Library/Java/JavaVirtualMachines/openjdk-17.jdk/Contents/Home/`
5. `jenv global 17` or `jenv local 17` depending on whether you want it to be global setting

Any other alternatives in getting Java 17 is acceptable (e.g. `sdkman`)

### Get Protobuf
1. `brew install protobuf`