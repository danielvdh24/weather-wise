<div align="center">
    <img src="https://github.com/danielvdh24/weather-wise/assets/51958337/1eecefde-971a-4696-94da-9cd0cc6fbecb" height="370px">
</div>


A lightweight desktop application built with Maven and JavaFX, featuring the [OpenWeatherMap](https://openweathermap.org/) API integration. Weather Wise offers a simple way to get the current weather information for any location, allowing users to make informed decisions based on the latest weather updates.

## Features :zap:
* City-based weather searching
* Geolocation-based weather searching
* Adaptive current weather icons
* Invalid input validation

## Getting Started :cloud:
### Executing the JAR File
To launch Weather Wise, complete the following steps:

1. Clone this repository into your preferred directory with `git clone https://github.com/danielvdh24/weather-wise.git`
2. Change your current directory to weather-wise-jar by executing the command `cd weather-wise/weather-wise/weather-wise-jar`
3. Download the JavaFX SDK 19.0.2.1 from [here](https://gluonhq.com/products/javafx/) (tick the box **Include older versions**), depending on your platform. Unzip and place the folder `javafx-sdk-19.0.2.1` (located in openjfx-19.0.2.1_windows-x64_bin-sdk) into `weather-wise/weather-wise/weather-wise-jar`
4. Execute the command `java --module-path javafx-sdk-19.0.2.1/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base -jar weather-wise.jar`
