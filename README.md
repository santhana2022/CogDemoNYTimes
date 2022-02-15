# ![logo](.images/nyt_app_logo.png) CAP-One Android Demo

## API Documentation:  [<u>NewYorkTimes Developers</u>](https://documenter.getpostman.com/view/196489/UVkgxeW6)

This sample application use the **Top Stories** API to fetch an array of articles currently on the specified section (
arts, business, ...).

##### ![logo](https://developer.nytimes.com/files/poweredby_nytimes_200b.png?v=1583354208360)

---

## The following values are supported article categories
|<!-- --> |<!-- --> |<!-- --> |<!-- --> |
| ------- | ------- | ------- | ------- |
| arts | insider | automobiles | magazine |
| books | movies | business | nyregion |
| fashion | obituaries | food  | opinion |
| health | home | politics | realestate |
| science | sports | sundayreview | technology |
| theater | t-magazine | travel | upshot |
| us | world |

## Proxy Layer Implementation
Instead of accessing NewYorkTimes API's directly from our mobile application, 
we have implemented a proxy layer in our cognizant backend and that will route our application's API calls to the NewYorkTimes developer service.

<img src=".images/application_architecture.png" alt="App Architecture" width="700" height="350"/>

## Basic application screens

|Home Screen |Politics Screen | Sports Screen |
| ------- | ------- | ------- |
| ![](.images/home_screen.png)| ![](.images/news_politics.png) | ![](.images/news_sports.png)|

