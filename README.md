Smart Adapters Library
======================

[![Build Status](https://travis-ci.org/mrmans0n/smart-adapters.svg?branch=master)](https://travis-ci.org/mrmans0n/smart-adapters) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-smart--adapters-green.svg?style=flat)](https://android-arsenal.com/details/1/1967)

Android library project that intends to simplify the usage of Adapters for ListView/GridView and RecyclerView. You won't have to code any boring adapter again!

It helps to keep boilerplate to a minimum and adds the possibility of easily changing between BaseAdapter / RecyclerView.Adapter adapter styles without changing any code. It also allows painless usage of multiple models / view types for the same list or grid - just add the different mappings between model objects and view objects.

Formerly part of [nl-toolkit](https://github.com/mrmans0n/nl-toolkit).

Adding to your project
----------------------

Add this to your dependencies:

```groovy
compile 'io.nlopez.smartadapters:library:1.2.2'
```

Usage
-----

### Adapter creation

If we got the typical list with one object mapped to one view, for example Tweet -> TweetView, it's as simple as this:

```java
SmartAdapter.items(myObjectList).map(Tweet.class, TweetView.class).into(myListView);
```

**Note** that we have to prepare a bit the view (TweetView in this case). Please read the ["Preparing your view classes"](#preparing-your-view-classes) section.

If we need to do a more complex list, with different models mapped to different views, we just have to add more `map` calls. Here is an example:

```java
SmartAdapter.items(myObjectList)
    .map(Tweet.class, TweetView.class)
    .map(String.class, ListHeaderView.class)
    .map(User.class, UserView.class)
    .into(myListView);
```

You can pass an AbsListView based control (ListView, GridView) or a RecyclerView to the `into` methods. The class will use the appropriate adapter class depending on which control you pass in there.

We can change the `items(...)` call for `empty()` if we want an adapter initialized with an empty array.

The calls from before return the adapter, so if you want to store it for manipulating it afterwards you can do it. For example:

```java
MultiAdapter adapter = SmartAdapter.empty().map(Tweet.class, TweetView.class).into(myListView);

// We can add more stuff. The list will update and refresh its views.
adapter.addItems(moreItems);

// And delete it if we want!
adapter.clearItems();
```

### Preparing your view classes

All your view classes must inherit BindableLayout<YourModelClass> so we got a common entrypoint for binding the model data to the view.

```java
public class TweetView extends BindableLayout<Tweet> {

    // [...]

    public TweetView(Context context) {
        // This is the constructor that should be implemented, because it's the one used internally
        // by the default builder.
        super(context);
    }

    @Override
    public int getLayoutId() {
        // This is mandatory, and should return the id for the view layout of this view
        return R.layout.view_tweet;
    }

    @Override
    public void onViewInflated() {
        // Here we should assign the views or use ButterKnife
        ButterKnife.inject(this);
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

I use an Android Studio template for creating the BindableLayouts. You can [find it here](https://gist.github.com/mrmans0n/0999fafdc1dd563411fd).

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
SmartAdapter.items(myObjectList)
    .map(Tweet.class, TweetView.class)
    .listener(myViewListener)
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

### Advanced usage with custom builders

If we want to display different cells depending on the data of a single model or something more convoluted, we can specify our own BindableLayoutBuilder interface for the classes to be instantiated. The library allows multiple views mapped to the same view object as long as you provide a specific implementation for the viewType method in a BindableLayoutBuilder.

Here we have an example of a custom BindableLayoutBuilder created for a hypothetical case where the view class depends on the values of the object. Ideally it's enough to just overwrite the viewType method and return values depending on the desired view class.

```java
public class TweetBindableLayoutBuilder extends DefaultBindableLayoutBuilder {

    @Override
    public ClassClass<? extends BindableLayout> viewType(@NonNull Object item, int position, Mapper mapper) {
        if (item instanceof Tweet) {
            // All the multiple bindings must be dealt with here and NOT get into the fallback
            Tweet tweet = (Tweet) item;
            if (tweet.hasGallery()) {
                return TweetGalleryView.class;
            } else if (tweet.hasImage()) {
                return TweetImageView.class;
            } else if (tweet.hasEmbeds()) {
                return TweetEmbedView.class;
            } else {
                return TweetView.class;
            }
        }
        // With this fallback we return control for all the other cases to be handled as the default use.
        return super.viewType(item, position, mapper);
    }
}

```

We can add it to the adapter like this:

```java
SmartAdapter.items(myObjectList)
    .listener(myViewListener)
    .map(Tweet.class, TweetView.class)
    .map(Tweet.class, TweetGalleryView.class)
    .map(Tweet.class, TweetImageView.class)
    .map(Tweet.class, TweetEmbedView.class)
    .map(OtherThing.class, OtherThingView.class)
    .builder(new TweetBindableLayoutBuilder())
    .into(myListView);
```

You can check more examples [in the default builders included with the library](https://github.com/mrmans0n/smart-adapters/tree/master/library/src/main/java/io/nlopez/smartadapters/builders). 

The idea behind the builders is that you can, given the object and its class, create the view class by yourself and return to the adapter.

Common issues
-------------

If you are already using RecyclerView in your project and have problems compiling, you can try setting the transitive property to false:

```groovy
compile ('io.nlopez.smartadapters:library:1.2.2') {
    transitive = false
}
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
