Easy WSDL

http://easywsdl.com


How to use generated classes:

1. In your android/java project create a new package folder (name of this folder should match the Package name of generated classes)
2. Go to src subfolder of generated package and copy all files to your project (to newly created folder).
3. In libs subfolder you will find all jars needed for generated classes. Copy all jars to your project and add references to them (every IDE has different way of adding refenreces to external jars)    
4. Add INTERNET permission to your AndroidManifest.xml file:
<uses-permission android:name="android.permission.INTERNET" />
5. Use the following code to connect to your webservice:

PGEOnlineIntegrationServiceSoapBinding service = new PGEOnlineIntegrationServiceSoapBinding();
service.MethodToInvoke(...);


Used libraries:

- ksoap2 v3.6.0 (http://simpligility.github.io/ksoap2-android/index.html). Please do not use any other version except the library included in the zip file.


Thanks for using EasyWsdl. We've spend much time to create this tool. We hope that it will simplify your development. If you like it, please upvote posts about it on stackoverflow and like us on Facebook (https://www.facebook.com/EasyWsdl).
This will help us promote the generator. If you find any problems then contact us and we will try to help you with your webservice.


Generator output:
