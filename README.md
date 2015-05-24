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

### Adapter creation

If we got the typical list with one object mapped to one view, for example Tweet -> TweetView, it's as simple as this:

```java
SmartAdapters.single(TweetView.class, Tweet.class).items(myObjectList).into(myListView);
```

**Note** that we have to prepare a bit the view (TweetView in this case). Please read the "Preparing your view classes" section.

If we need to do a more complex list, with different models mapped to different views, we can do it too. Here is an example:

```java
SmartAdapters.multi()
    .map(Tweet.class, TweetView.class)
    .map(String.class, ListHeaderView.class)
    .map(User.class, UserView.class)
    .items(myObjectList)
    .into(myListView);
```

You can pass an AbsListView based control (ListView, GridView) or a RecyclerView to the `into` methods. The class will use the appropriate adapter class depending on which control you pass in there.

The `items` method is optional. You can always add items later to the adapter and the classes will start with an empty array.

The calls from before return the adapter, so if you want to store it for manipulating it afterwards you can do it. For example:

```java
SingleAdapter<TweetView, Tweet> adapter = SmartAdapters.single(TweetView.class).into(myListView);

// We can add more stuff. The list will update and refresh its views.
adapter.addItems(moreItems);

// And delete it if we want!
adapter.clearItems();
```

### Preparing your view classes

All your view classes must inherit BindableLayout<YourModelClass> so we got a common entrypoint for binding the model data to the view.

```java
public class TweetView extends BindableLayout<Tweet> {

    public TweetView(Context context) {
        super(context);
        initView();
    }

    public void initView() {
        // Here we should assign the layout, inflate the views (or use butterknife or something similar), etc.
        // [...]
    }

    @Override
    public void bind(Tweet tweet) {
        // In here we assign the model information values to our view widgets

        // Examples:
        tweetText.setText(tweet.getText());
        authorText.setText(tweet.getAuthor());

        // and so on!
    }
}
```

A nice side effect is that we can pretty much switch back and forth to using ListView or RecyclerView without having to change anything in these views. We also got some more granular control over the events for those view widgets. We could pretty much add different onClick events to the row, to some button inside of it, etc.

We can notify the listener with some specific calls to be able to handle the events wherever the adapter is being instantiated, instead of doing it all inside the view code (which would be pretty messy).

```java
    public void bind(MyModel model) {
        // Set a global click handler
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemAction(ROW_PRESSED);
            }
        });
        favoriteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemAction(FAVORITE_PRESSED);
            }
        });
    }
```

### Assigning listeners

If we want to control any event in our view classes at the adapter level, we can do it. We just have to add a listener to the adapter, and it could be done when instantaiting it or after.

For example:

```java
SmartAdapters.single(TweetView.class)
    .listener(myViewListener)
    .items(myObjectList)
    .into(myListView);
```

The listener would be like this:

```java
myViewListener = new ViewEventListener<Tweet>() {
    @Override
    public void onViewEvent(int actionId, Tweet item, View view) {
        // actionId would be any constant value you used in notifyItemAction.
    }
};
```

### Android Annotations EViewGroup support

Android Annotations generated @EViewGroup classes are instantiated by calling a static `.build()` method on the view class instead of using the constructor. In fact, if you used the constructor there, you would probably ended up getting a runtime exception.

There is a flag in the library for supporting this automatically: `.aa(true)`. You have to use the generated class instead of the base class when performing the call.

For example:

```java
SmartAdapters.single(TweetView_.class)
    .listener(myViewListener)
    .aa(true)
    .items(myObjectList)
    .into(myListView);
```

Contributing
------------
Forks, patches and other feedback are welcome.

Creators
--------

Nacho López - Github [@mrmans0n](https://github.com/mrmans0n) - Twitter [@mrmans0n](https://twitter.com/mrmans0n) - Blog [nlopez.io](http://nlopez.io)

License
-------

[MIT License](LICENSE)
