# piweb
Simple REST API for Raspberry Pi based on http://pi4j.com/

Store your portconfiguration in web.xml property online.blickle.pi.PortConfiguration as JSON string, deploy the WAR to Tomcat and your REST service is up and running!

API:
```xml
<resources base="http://localhost:8080/piweb/v1/">
 <resource path="/ports">
    <method id="getPortStatus" name="GET">
      <response><representation mediaType="application/json"/></response>
    </method>
    <resource path="/{id}/">
      <param name="id" style="template" type="xs:string"/>
      <method id="getPortStatus" name="GET">
        <response><ns2:representation element="raspberryPort" mediaType="application/json"/></response>
      </method>
    </resource>
    <resource path="/{id}/{value}">
      <param name="id" style="template" type="xs:string"/>
      <param name="value" style="template" type="xs:string"/>
      <method id="setPortStatus" name="POST">
        <response><ns2:representation element="raspberryPort" mediaType="application/json"/></response>
      </method>
    </resource>
 </resource>
 <resource path="/configuration">
    <method id="getPortConCollection" name="GET">
      <response><ns2:representation element="portDescriptionList" mediaType="application/json"/></response>
    </method>
  </resource>
</resources>
```
