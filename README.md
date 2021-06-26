# Project 2 - Flixster

**Flixster** is a movie browsing Android app--similar to Fandango and Rotten Tomatoes--that lets a user view and scroll through a list of movies that are pulled from an online API (themoviedb). 

Submitted by: **Hannah**

Time spent: **12** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **view a list of movies**
* [X] While loading, **placeholder images** appear in place of movie posters and backdrops
* [X] User are able to view the app in both **portrait and landscape** mode
* [X] User can click on a movie to **view more details** about it

The following **bonus** features are implemented:

* [X] All movie posters and backdrops come with **rounded corners**
* [X] Added additional features, such as a visual response to clicking a movie and themed starred ratings, to **improve the user interface**
* [X] Embedded **YouTube trailers for movies**, which users can view by clicking in the movie backdrop

The following **additional** features are implemented:

* [X] A **genre list** to help users identify the genre of movies
* [X] A **back button**, so users can return to the Flixster homescreen without using their phone's back button

## Video Walkthrough

Here's a walkthrough of implemented user stories:

# Flixster in Portrait Mode
<img src='https://github.com/hannahkm/Flixster/raw/master/PortraitFlixster.gif' title='Video Walkthrough' width='250' alt='Video Walkthrough' />

# Flixster in Landscape Mode
<img src='https://github.com/hannahkm/Flixster/raw/master/LandscapeFlixster.gif' title='Video Walkthrough' width='500' alt='Video Walkthrough' />

GIFs created with [Kap](https://getkap.co/).

## Notes

* I had trouble accessing the genre API. The original JSON of movies had a list of integer values for the genres, each of which translated to a different description. I had to pull from another API in order to translate these.
* I also had difficulty with implementing the back button. At first, it would crash the app because I was adding unnecessary checks. Removing those extra steps fixed it!

## License

    Copyright [2021] [Hannah Kim]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
