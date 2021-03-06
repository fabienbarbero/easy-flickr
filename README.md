# Flickr API

Flickr API is a Java / android API implementation to access the Flickr web services defined [here](https://www.flickr.com/services/api/). The API uses only the REST-JSON requests to have the minimum payload size (which is useful with android device which do not have a high speed network access).


## Import in your projects

### For simple Java apps

```xml
<dependency>
    <groupId>com.github.fabienbarbero</groupId>
    <artifactId>flickr-api</artifactId>
    <version>1.2</version>
</dependency>
```

### For android apps

We must exclude the base httpClient dependencies to use the android implementation.
```
dependencies {
    compile ('com.github.fabienbarbero:flickr-api:1.2') {
        exclude group: 'org.apache.httpcomponents', module: 'httpclient'
    }
    compile 'org.apache.httpcomponents:httpclient-android:4.3.5.1'
    ...
}
```


## Getting started

The calls to Flickr are quiet easy:

```java
Flickr flickr = new Flickr("my.api.key", "my.api.secret", "http://localhost/callback", "read", new File("flickr.conf"));

// Check if a user is already logged
if(!flickr.isFirstStart()) {
    // Get the authorization URL to allow the application to use the Flickr services
    String url = flickr.getAuthorizationUrl();
    // Show the previous URL in a browser

    ...

    // Verify the token 
    String verifier = ...;
    String token = ...;
    flickr.verifyToken(verifier, token);
}

// Authenticate the user
UserInfo user = flickr.getUser();

// Use the services
Paginated<Photo> photos = flickr.getPhotosService().getRecentlyUpdated(50, 0);
...
```

## Features

The library do not supports all Flickr features. Here are the supported :

 - get the users contacts
 - get the cameras brand list
 - get the favorites photos of users
 - get the users galleries
 - get the groups and their photos
 - get the people (search user, get users informations ...)
 - get the photos and the sets
 - get the statistics on photos, set ...
 - upload new photos

