# famodulus-server
Server for outsourcing calculations in multiplicative groups with prime modulus

## Enabling Montgomery Multiplication

 MAVEN_OPTS='-XX:+UseMontgomeryMultiplyIntrinsic -XX:+UseMontgomerySquareIntrinsic' mvn exec:java

## Change Listening Port

 MAVEN_OPTS='-Dfamodulus.base=http://localhost:8082/' mvn exec:java
