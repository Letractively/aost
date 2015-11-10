

# Introduction #

jQuery selector improves the speed performance in IE. jQuery cache is a mechanism to further improve the speed by reusing the found DOM reference for a given selector. But you should keep in mind that the jQuery locating time may be just a small portion of the round trip time from Tellurium core to Selenium core, thus, the improvement of jQuery cache over regular jQuery selector has upper bound. However, jQuery cache is fundamental to Tellurium because it is the first step for us to create our own Tellurium Engine.

# The Cache Mechanism #

## Tellurium Core Cache Support ##

When you choose to use jQuery Cache mechanism, Tellurium core will pass the jQuery selector and a meta command to Tellurium Engine, which is embedded inside Selenium core at the current stage. The format is as follows taking the Tellurium issue searching UI as an example,

```
jquerycache={
  "locator":"form[method=get]:has(#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) #can",
  "optimized":"#can",
  "uid":"issueSearch.issueType",
  "cacheable":true,
  "unique":true
}
```

where _locator_ is the regular jQuery selector and _optimized_ is the optimized jQuery selector by the jQuery selector optimizer in Tellurium core. The _locator_ is used for child UI element to derive a partial jQuery selector from its parent. The _optimized_ will be used for actual DOM searching.

The rest three parameters are meta commands. _uid_ is the UID for the corresponding jQuery selector and _cacheable_ tells the Engine whether to cache the selector or not. The reason is that some UI element on the web is dynamic, for instance, the UI element inside a data grid. As a result, it may not be useful to cache the jQuery selector.

The last one, _unique_, tells the Engine whether the jQuery selector expects multiple elements or not. This is very useful to handle the case that jQuery selector is expected to return one element, but actually returns multiple ones. In such a case, a Selenium Error will be thrown.

To make the cache option configurable in tellurium UI module, Tellurium introduces an attribute _cacheable_ to UI object, which is set to be true by default. For a Container type object, it has an extra attribute _noCacheForChildren_ to control whether to cache its children.

One example UI module is defined as follows,

```
  ui.Table(uid: "issueResultWithCache", cacheable: "true", noCacheForChildren: "true", clocator: [id: "resultstable", class: "results"], group: "true") {
 
       ......

      //define table elements
      //for the border column
      TextBox(uid: "row: *, column: 1", clocator: [:])
      TextBox(uid: "row: *, column: 8", clocator: [:])
      TextBox(uid: "row: *, column: 10", clocator: [:])
      //For the rest, just UrlLink
      UrlLink(uid: "all", clocator: [:])
    }
```

Where the _cacheable_ can overwrite the default value in the UI object and _noCacheForChildren_ in the above example will force Tellurium to not cache its children.

## Tellurium Engine Cache Definition ##

On the Engine side the cache is defined as

```
    //global flag to decide whether to cache jQuery selectors
    this.cacheSelector = false;

    //cache for jQuery selectors
    this.sCache = new Hashtable();
    
    this.maxCacheSize = 50;

    this.cachePolicy = discardOldCachePolicy;
```

The cache system includes a global flag to decide whether to use the cache capability, a hash table to store the cached data, a cache size limit, and a cache eviction policy once the cache is filled up.

The cached data structure is defined as

```
//Cached Data, use uid as the key to reference it
function CacheData(){
    //jQuery selector associated with the DOM reference, which is a whole selector
    //without optimization so that it is easier to find the reminding selector for its children
    this.selector = null;
    //optimized selector for actual DOM search
    this.optimized = null;
    //jQuery object for DOM reference
    this.reference = null;
    //number of reuse
    this.count = 0;
    //last use time
    this.timestamp = Number(new Date());
};
```

## Cache Eviction Policies ##

Tellurium Engine provides the following cache eviction policies:

  * DiscardNewPolicy: discard new jQuery selector.
  * DiscardOldPolicy: discard the oldest jQuery selector measured by the last update time.
  * DiscardLeastUsedPolicy: discard the least used jQuery selector.
  * DiscardInvalidPolicy: discard the invalid jQuery selector first.

## Cache Data Validation ##

It is important to know when the cached data become invalid. There are three mechanisms we can utilize here:
  1. Listen for page refresh event and invalidate the cache data accordingly
  1. Intercept Ajax response to decide when the web is update and if the cache needs to be updated.
  1. Validate the cached jQuery selector before using it.

Right now, Tellurium Engine uses the 3rd mechanism to check if the cached data is valid or not. The first two mechanisms are still under development.

## Cache Work Flow ##

Whenever Tellurium Core passes a locator to the Engine, the Engine will first look at the meta command _cacheable_. If this flag is true, it will first try to look up the DOM reference from the cache. If no cached DOM reference is available, do a fresh jQuery search and cache the DOM reference, otherwise, validate the cached DOM reference and use it directly. If the _cacheable_ flag is false, the Engine will look for the UI element's ancestor by its UID and do a jQuery search starting from its ancestor if possible.

# jQuery Selector Cache APIs #

Tellurium Core provides the following methods for jQuery Selector cache control,

  * _public void enableSelectorCache()_: Enable jQuery selector cache

  * _public boolean disableSelectorCache()_: Disable jQuery selector cache

  * _public boolean cleanSelectorCache()_: Cleanup the whole jQuery selector cache

  * _public boolean getSelectorCacheState()_: Test if the cache is enabled or not in Engine

  * _public void setCacheMaxSize(int size)_: Set the cache maximum size

  * _public int getCacheSize()_: Get the cache current size

  * _public int getCacheMaxSize()_: Get the cache maximum size

  * _public Map<String, Long> getCacheUsage()_: Get the cache usage

  * _public void useDiscardNewCachePolicy()_: Use DiscardNewCachePolicy

  * _public void useDiscardOldCachePolicy()_: Use DiscardOldCachePolicy

  * _public void useDiscardLeastUsedCachePolicy()_: Use DiscardLeastUsedCachePolicy

  * _public void useDiscardInvalidCachePolicy()_: Use DiscardInvalidCachePolicy

  * _public String getCurrentCachePolicy()_: Get the current cache policy


# Performance #

## The Cache Eviction Policies ##

To see how the cache eviction policies will affect the test speed, we run tests based on the Tellurium Issue Page using different cache policies. The results are illustrated in the following chart.

http://tellurium-users.googlegroups.com/web/jQueryCachePolicyPerformance4IE.png?gda=LDvGu1UAAAD5mhXrH3CK0rVx4StVj0LYQUEeNsp3TbIKkdXGThnPyu0KMCVt8pBgyksNWzxEYx1pD13jRZarYmmgVWF-bdp9c5ap1mUA98uo2agjH0hUOhrtYix3qocOGWUY90Yyf_g&gsc=6qDg_QsAAABfJKqj6CAYYfcNy0ZOBIOX

It looks like the DiscardOldPolicy cache policy performans the best and thus, the Engine sets the default policy as DiscardOldPolicy. For different web tests, you may like to select different cache eviction policy to fit for your need.

## Speed Performance of jQuery Selector Cache ##

We choose Ajaxslt, regular jQuery selector without cache, and jQuery selector with cache for speed comparison. The tests are categorized into four:

  1. Tellurium Issue page test with group locating
  1. Tellurium Issue page test without group locating
  1. Tellurium Issue search
  1. Tellurium Issue select

Where the last one is an extreme case to test the speed performance for reusable jQuery selectors. All tests are run in IE 6.

The test results are listed in the following table (Unit: second),

| **Locator** | **Group** | **NoGroup** | **Issue Search** | **Issue Select**|
|:------------|:----------|:------------|:-----------------|:----------------|
| Ajaxslt XPath | 30.886    | 33.035      | 11.859           | 11.329          |
| jQuery Selector | 18.756    | 20.355      | 7.941            | 7.181           |
| Cached Selector | 16.975    | 18.882      | 6.821            | 5.215           |

And the results can be charted as follows,

http://tellurium-users.googlegroups.com/web/jQueryCachePerformance4IE.png?gda=YG0VRE8AAAD5mhXrH3CK0rVx4StVj0LYVJZmquJiAgwBG5vtpupPemH3_cwn9m4Fn1M7z_Uoi71BFv4kq5hHqoCvWumv4MuFnHMhSp_qzSgvndaTPyHVdA&gsc=TF3_DwsAAACSjCyx_KLYdV7x32Mb1NjE

We can observe the following facts

  * The jQuery selector cache can further improve the test speed
  * The jQuery selector cache can improve the Tellurium Issue search speed up to 14% over the regular jQuery selector.
  * For the extreme case, the jQuery select cache can improve the speed by 27% over the regular jQuery selector.

# Conclusions #

As we said in the introduction section, the jQuery DOM search may be only a small portion of selenium command round trip time, thus, the speed improvement of jQuery selector cache is limited by that upper bound. However, jQuery selector cache is critical to Tellurium Engine and it lays the ground for Tellurium new test driving Engine. For further speed improvement, please wait for our new Tellurium Engine.

