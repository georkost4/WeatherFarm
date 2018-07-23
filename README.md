# Weather Farm

## About
This repository was created to complete my CapStone (final project) for the Udacity Android Developer Nanodeegre program. More details about wireframes and project set up  can be found in [this](https://docs.google.com/document/d/1kfZbBL_QTtPswxH5rGvxP1bpDnqrdSG7aeU-KiPLwKg/edit?usp=sharing) google document.

## Intro
What I built is an Android app that communicates with a remote API and displays info from that API. More specifically the application pulls geo_json from [Agro monitoring API](https://agromonitoring.com/api/agro-api). This API provides weather forecast , satellite images for a specific position (latitude , longitude) or a user - defined polygon. Along with weather forecast the API provides detailed weather info such as  UV index and soil moisture. These geo_json will come handy mostly to farmers but you can use it as a regular weather app.

## Features

 - User defined map polygons
 - Current weather forecast about specific long,lat position or polygon
 - 5 day weather forecast
 - Soil moisture , UV index info about specific long,lat position or polygon


 ## Libraries Used

 - *Retrofit* for network calls
 - *GSon* for converting the JSON response into POJO from the API
 - *Google Maps* for drawing a polygon on the Map
 - *AdMob* for displaying user ads


 ## Application Screenshots (**coming soon**)