Smart Adapers Library
=====================

[![Build Status](https://travis-ci.org/mrmans0n/smart-adapters.svg?branch=master)](https://travis-ci.org/mrmans0n/smart-adapters)

Android library project that intends to simplify the usage of Adapters for ListView/GridView and RecyclerView.

It helps to keep boilerplate to a minimum and adds the possibility of easily changing between BaseAdapter / RecyclerView.Adapter types without changing almost any code.

Formerly part of [nl-toolkit](https://github.com/mrmans0n/nl-toolkit).

Adding to your project
----------------------

**NOTE** The library is in heavy development at this time so it's possible it's still not available in central repositories.

You should add this to your dependencies:

```groovy
compile 'io.nlopez.smartadapters:library:1.0.0'
```

Usage
-----

All your view classes must inherit BindableLayout<YourModelClass> so we got a common entrypoint for binding the model data to the view.

```java
public class TweetView extends BindableLayout<Tweet> {

    public TweetView(Context context) {
        super(context);
        initView();
    }

    public void initView() {
        // Here we should assign the layout, inflate the views (or use butterknife or something similar), etc.
    }

    @Override
    public void bind(Tweet tweet) {
        // In here we assign "tweet" values to our view widgets

        // Examples:
        tweetText.setText(tweet.getText());
        authorText.setText(tweet.getAuthor());

        // and so on!
    }

}

```


If we got the typical list with one object mapped to one view, for example Tweet -> TweetView, it's as simple as this:

```java
SmartAdapters.single(TweetView.class, Tweet.class).into(myListView);
```

If we need to do a more complex list, with different models mapped to different views, we can do it too. Here is an example:

```java
SmartAdapters.multi()
    .map(Tweet.class, TweetView.class)
    .map(String.class, ListHeaderView.class)
    .map(User.class, UserView.class)
    .into(myListView);
```

Contributing
------------
Forks, patches and other feedback are welcome.

Creators
--------

Nacho López - Github [@mrmans0n](https://github.com/mrmans0n) Twitter [@mrmans0n](htttps://twitter.com/mrmans0n) Blog [nlopez.io](http://nlopez.io)

License
-------

[Read here.](LICENSE)
