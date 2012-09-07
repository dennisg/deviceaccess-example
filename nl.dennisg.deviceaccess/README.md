README
---

OSGi device access example
---

This is a very, very basic example project that shows how the DeviceManager (of the OSGi device access spec) interacts with devices, drivers and DriverLocators.

An implementation can be found as a subproject to [Apache Felix](http:///felix.apache.org) in [subversion](http://svn.apache.org/repos/asf/felix/trunk/deviceaccess)

This example uses [BndTools](http://bndtools.org/) in Eclipse and/ or Apache Ant.

Just check out the project, and execute 'ant run'  in the folder called 'nl.dennisg.deviceaccess'.

In another terminal window touch any file in the 'devices' folder that was created automatically. The Device Manager will start the Driver refinement automatically.

To show how special, more specific drivers, are matched, create a file called 'dennisg.txt'
( You can also try creating '<your-nick>.txt', who knows... ;-) ). In this case, another driver with a higher match is selected and attached...

For more information you can contact me [at work](mailto:dennis.geurts@luminis.eu) or [privately](dennisg@dennisg.nl). 

