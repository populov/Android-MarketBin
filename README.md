MarketBin
=========
Helps to detect which Android app stores are installed on device, and make links to your applications in those stores.

Examples
--------
Handle "Show In Market" click
```java
MarketLocator locator = new MarketLocator(getActivity());
IMarketDescriptor market = locator.findFirst(MarketBin.getAllKnown());
MarketUI.showAppInMarket(getActivity(), market, "com.haunted.office.buzz");
```
Display clicable link in a TextView
```java
MarketUI.bindAppLink(textView, market, "com.haunted.office.buzz");
```
Get list of Android app store clients installed on device
```java
MarketLocator locator = new MarketLocator(getActivity());
List<IMarketDescriptor> availableMarkets = locator.findAll(MarketBin.getAllKnown();, true, true);
```
**Note:** MarketBin doesn't check if your app is available in those stores, only checks if they are available on your device. So, if you want to check that a subset of certain markets (where your app is published) is installed on user's device, you can make your own list for verification. The order in this list is significant for search sequence. So it's recommended to place most desirable items in the beginning of list and vice versa. Also I recommend to place at the end of list a "fallback" item, that shows a google play website in browser, if no available markets detected. 
```java
IMarketDescriptor[] officeBuzzPublishedIn = new IMarketDescriptor[] {
    MarketBin.getGooglePlay(),
    MarketBin.getSamsungApps(),
    MarketBin.getSlideMe(),
    MarketBin.getGooglePlayWeb()
};
IMarketDescriptor market = locator.findFirst(Arrays.asList(officeBuzzPublishedIn));
```
Dynamic extending
------------
You can add your own markets without need to modify library source code. Just create a new MarketDescriptor item With market name, market client package name (if you know it), and template of package location URL (you can find it in store API documentation), where **%s** is a place, where your application id (usually package name) will be placed.
```java
IMarketDescriptor googlePlay = new MarketDescriptor("Google Play", "market://details?id=%s", "com.android.vending");
```
Odd use
-------
Market locator can be used to check whether some application is installd or some URI scheme handler is present. So, you can, for example, easily check if user has Bitcoin wallet installed and make clicable links for Bitcoin addresses.
```java
MarketLocator locator = new MarketLocator(getActivity());
IMarketDescriptor donation = new MarketDescriptor("Bitcoin Donation", "bitcoin:%s", null);
boolean bitcoinProtocolHandlerFound = locator.findByIntent(Arrays.asList(donation)) != null;
if (bitcoinProtocolHandlerFound)
    MarketUI.bindAppLink(textView, donation, "17g6FWQh8iF8C2MAQhoyYAvxtpYXdMbfbd");
```
Use in Android Studio
--------------
### Online dependency ###
The easyest way to use - you don't need to build project yourself, just add a few lines in your *gradle.config*.

1. Add Haunted soft maven repository in project repositories section (don't mix it up with buildscript repositories, that usually goes first).

    ```groovy
    repositories {
        mavenCentral()
        maven { url "http://dl.bintray.com/haunted-soft/maven" }
    }
    ```
2. add project depencency as usual

    ```groovy
    dependencies {
        compile 'com.haunted.marketbin:MarketBin:+@aar'
        ...
    }
    ```

### Local dependency (build from source) ###
If you want to have latest version, that has not been published yet, or build from modified sources, the easyest way is to depend on local Maven repository in your project.

1. Download sources, build MarketBin project with gradle and execute **uploadArchives** task to publish library in local Maven repository.

    ```
    git clone git@github.com:Haunted-Soft/MarketBin.git
    cd MarketBin
    gradle build uploadArchives
    ```
2. Add mavenLocal repository in project repositories section (don't mix it up with buildscript repositories, that usually goes first)

    ```groovy
    repositories {
        mavenCentral()
        mavenLocal()
    }
    ```
Now add project dependency as usual (see above).
Donations
=========
You can donate me some virtual coins, if you like MarketBin library and especially if it helps you earn real money :)

Bitcoin: [17g6FWQh8iF8C2MAQhoyYAvxtpYXdMbfbd](bitcoin:17g6FWQh8iF8C2MAQhoyYAvxtpYXdMbfbd)

Litecoin: [Lcz35NNdPap6X8GVgudVP9nwWxbYMvRgpi](litecoin:Lcz35NNdPap6X8GVgudVP9nwWxbYMvRgpi)
