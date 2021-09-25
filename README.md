# FihHuda/فيه هديً
It is a muslim application for reading and listening quran, reading Azkar.

# Which tools Used :
* [Material Design]
* [MVVM architecture Pattern]
* [Rebosotiry & singlton design Pattern]
* [Retrofit for dealing with RESTApi]
* [Services & thread]
* [Live Data]
* [Shared preferences for small local ecache]
* [Gson converter]
* [RecyclerView & Fragment]
* [bottom navigation to move between fragments]
* [custom bottomSheet]


## Now lets go to see our Application !

<div>
<img width="20%" height="350"  src="https://user-images.githubusercontent.com/55314273/128643995-3e43f32c-b2b6-4a4f-aa62-0a1b8fbb12c2.jpg" >
<img width="20%" height="350" src="https://user-images.githubusercontent.com/55314273/128644248-7b639d43-da26-4f31-95fe-ead21a70132e.jpg">
<img width="20%" height="350" src="https://user-images.githubusercontent.com/55314273/128644266-63bcb783-8cd9-4ef9-b954-642bdbc57d9b.jpg">
<img width="20%" height="350" src="https://user-images.githubusercontent.com/55314273/128644420-443751d8-d903-4267-bffb-6b316af9eaba.jpg">
<img width="20%" height="350" src="https://user-images.githubusercontent.com/55314273/128644392-9ac6ff8b-0ebe-4d7a-a64c-6a1eaac70472.jpg">                         

                         
                         
## Learned While Migration to Kotlin
- "AGPBI: {"kind":"error","text":"Default interface methods are only supported starting with Android N (--min-api 24): Landroidx/core/internal/view/SupportMenuItem;setTooltipText(Ljava/lang/CharSequence;)Landroid/view/MenuItem;","sources":[{}],"tool":"D8"}"
Solced by

```
 compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

```
- 64K limit -> make `minSdk 21` so **multidex** is **enabled by default**.
- large size images -> generate different images using [appicon](https://appicon.co/#image-sets)